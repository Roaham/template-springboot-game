#!/usr/bin/env python3
"""Create sample data without duplicating existing records."""

from __future__ import annotations

import argparse
import json
import sys
from dataclasses import dataclass
from typing import Any
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen


DEFAULT_BASE_URL = "http://localhost:8082/api/v1"

SEED_DATA = {
    "categories": [
        "Action", "Adventure", "RPG", "Strategy", "JRPG", "Social Simulation", "Visual Novel"
    ],
    "platforms": [
        "PC", "PlayStation 5", "Xbox Series X/S", "Nintendo Switch", "Nintendo Switch 2",
        "Magnavox Odyssey", "Atari 2600", "Intellivision", "ColecoVision", "Atari 5200",
        "NES", "Master System", "Atari 7800", "PC Engine", "Super Nintendo",
        "Mega Drive", "Neo-Geo", "PlayStation", "Sega Saturn", "Nintendo 64"
    ],
    "developers": [
        "P-Studio",
        "Nintendo",
        "FromSoftware",
        "CD Projekt Red",
        "Valve",
        "Acrobatic Chirimenjako"
    ],
    "editors": [
        "Nintendo",
        "Bandai Namco",
        "CD Projekt",
        "Valve",
        "Sony Interactive Entertainment",
        "Xbox Game Studios",
        "Shueisha"
    ]
}

GAMES = [
    {
        "name": "Schrodinger's Call",
        "description": "When the world is coming to an end, who would you want to speak to one last time? Schrödinger’s Call is a visual novel that lets you experience the pain and salvation of human connection, as if you were turning the pages of a picture book.",
        "category": ["Adventure", "Visual Novel"],
        "developer": "Acrobatic Chirimenjako",
        "editor": "Shueisha",
        "platforms": ["Nintendo Switch", "PC", "Nintendo Switch 2"],
        "mark": 92,
        "photos": [
            "https://picsum.photos/200",
            "https://picsum.photos/200"
        ],
    },
    {
        "name": "Elden Ring",
        "description": "Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring and become an Elden Lord in the Lands Between.",
        "category": ["Action", "RPG"],
        "developer": "FromSoftware",
        "editor": "Bandai Namco",
        "platforms": ["PC", "PlayStation 5", "Xbox Series X/S"],
        "mark": 96,
        "photos": [
            "https://picsum.photos/200"
        ],
    },
    {
        "name": "The Legend of Zelda: Ocarina of Time",
        "description": "A young boy from the forest is tasked with saving the land of Hyrule from the thief Ganondorf by traveling through time.",
        "category": ["Action", "Adventure"],
        "developer": "Nintendo",
        "editor": "Nintendo",
        "platforms": ["Nintendo 64"],
        "mark": 99,
        "photos": [
            "https://picsum.photos/200"
        ],
    },
    {
        "name": "Portal 2",
        "description": "The cooperative mode features a unique two-player campaign with its own story, test chambers, and two new player characters.",
        "category": ["Action", "Strategy"],
        "developer": "Valve",
        "editor": "Valve",
        "platforms": ["PC"],
        "mark": 95,
        "photos": [
            "https://picsum.photos/200"
        ],
    },
    {
        "name": "Persona 5 Royal",
        "description": "Don the mask of Joker and join the Phantom Thieves of Hearts as you break the chains of modern society.",
        "category": ["RPG", "JRPG", "Social Simulation"],
        "developer": "P-Studio",
        "editor": "Sony Interactive Entertainment",
        "platforms": ["PC", "PlayStation 5", "Nintendo Switch"],
        "mark": 95,
        "photos": [
            "https://picsum.photos/200"
        ],
    }
]

REVIEWS = [
    {
        "game": "Schrodinger's Call",
        "author": "Alex",
        "content": "Una novela visual muy emotiva y cuidada.",
        "mark": 90,
    },
    {
        "game": "Schrodinger's Call",
        "author": "Marta",
        "content": "La historia destaca por sus personajes y ambientación.",
        "mark": 94,
    },
    {
        "game": "Elden Ring",
        "author": "Carlos",
        "content": "Un mundo abierto increíble y desafiante.",
        "mark": 98,
    },
    {
        "game": "The Legend of Zelda: Ocarina of Time",
        "author": "Laura",
        "content": "Una obra maestra atemporal.",
        "mark": 100,
    },
    {
        "game": "Portal 2",
        "author": "David",
        "content": "Puzles perfectos y un diseño de niveles brillante.",
        "mark": 95,
    },
    {
        "game": "Persona 5 Royal",
        "author": "Elena",
        "content": "Excelente banda sonora y un sistema de combate adictivo.",
        "mark": 96,
    }
]

@dataclass
class Api:
    base_url: str
    timeout: float
    dry_run: bool = False

    def request(self, method: str, path: str, body: dict[str, Any] | None = None) -> Any:
        url = f"{self.base_url.rstrip('/')}/{path.lstrip('/')}"
        data = json.dumps(body).encode("utf-8") if body is not None else None
        request = Request(
            url,
            data=data,
            headers={"Accept": "application/json", "Content-Type": "application/json"},
            method=method,
        )

        try:
            with urlopen(request, timeout=self.timeout) as response:
                raw = response.read()
                return json.loads(raw) if raw else None
        except HTTPError as error:
            details = error.read().decode("utf-8", errors="replace")
            raise RuntimeError(f"{method} {url} -> HTTP {error.code}: {details}") from error
        except URLError as error:
            raise RuntimeError(f"No se pudo conectar con {url}: {error.reason}") from error


def normalized(value: str) -> str:
    return value.strip().casefold()


def get_items(api: Api, resource: str) -> list[dict[str, Any]]:
    result = api.request("GET", resource)
    if not isinstance(result, list):
        raise RuntimeError(f"La respuesta de GET /{resource} no es una lista")
    return result


def get_or_create_named(
        api: Api,
        resource: str,
        name: str,
        photos: list[str] | None = None,
) -> str:
    existing = next(
        (item for item in get_items(api, resource)
         if normalized(item.get("name", "")) == normalized(name)),
        None,
    )
    if existing is not None:
        print(f"  existe {resource[:-1]}: {name} ({existing['id']})")
        return existing["id"]

    if api.dry_run:
        print(f"  crear {resource[:-1]}: {name}")
        return f"dry-run-{resource}-{normalized(name)}"

    payload = {"name": name}
    if photos:
        payload["photos"] = photos
    created = api.request("POST", resource, payload)
    print(f"  creado {resource[:-1]}: {name} ({created['id']})")
    return created["id"]


def as_names(value: str | list[str]) -> list[str]:
    return value if isinstance(value, list) else [value]


def create_game(api: Api, game: dict[str, Any], ids: dict[str, dict[str, str]]) -> str:
    existing = next(
        (item for item in get_games(api) if normalized(item.get("name", "")) == normalized(game["name"])),
        None,
    )
    if existing is not None:
        print(f"  existe juego: {game['name']} ({existing['id']})")
        return existing["id"]

    payload = {
        "name": game["name"],
        "description": game["description"],
        "platformIds": [
            ids["platforms"][name] for name in as_names(game["platforms"])
        ],
        "developerIds": [
            ids["developers"][name] for name in as_names(game["developer"])
        ],
        "editorIds": [
            ids["editors"][name] for name in as_names(game["editor"])
        ],
        "categoryIds": [
            ids["categories"][name] for name in as_names(game["category"])
        ],
        "photos": game.get("photos", []),
        "mark": game["mark"],
    }

    if api.dry_run:
        print(f"  crear juego: {game['name']}")
        return f"dry-run-game-{normalized(game['name'])}"

    created = api.request("POST", "games", payload)
    print(f"  creado juego: {game['name']} ({created['id']})")
    return created["id"]


def get_games(api: Api) -> list[dict[str, Any]]:
    result = api.request("GET", "games?size=1000")
    if not isinstance(result, dict) or not isinstance(result.get("content"), list):
        raise RuntimeError("La respuesta de GET /games no tiene el formato paginado esperado")
    return result["content"]


def create_review(api: Api, game_id: str, review: dict[str, Any]) -> None:
    reviews = api.request("GET", f"games/{game_id}/reviews")
    if not isinstance(reviews, list):
        raise RuntimeError(f"La respuesta de las reseñas del juego {game_id} no es una lista")

    existing = next(
        (
            item for item in reviews
            if normalized(item.get("author", "")) == normalized(review["author"])
            and item.get("content") == review["content"]
        ),
        None,
    )
    if existing is not None:
        print(f"  existe reseña de {review['author']} ({existing['id']})")
        return

    payload = {
        "author": review["author"],
        "content": review["content"],
        "mark": review["mark"],
    }
    if api.dry_run:
        print(f"  crear reseña de {review['author']}")
        return

    created = api.request("POST", f"games/{game_id}/reviews", payload)
    print(f"  creada reseña de {review['author']} ({created['id']})")


def seed(api: Api) -> None:
    ids: dict[str, dict[str, str]] = {}

    for resource, names in SEED_DATA.items():
        print(f"\n{resource}:")
        ids[resource] = {
            name: get_or_create_named(
                api,
                resource,
                name,
                [f"https://images.example.com/{resource}/{normalized(name).replace(' ', '-')}.jpg"],
            )
            for name in names
        }

    print("\ngames:")
    game_ids: dict[str, str] = {}
    for game in GAMES:
        game_ids[game["name"]] = create_game(api, game, ids)

    print("\nreviews:")
    for review in REVIEWS:
        create_review(api, game_ids[review["game"]], review)


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--base-url", default=DEFAULT_BASE_URL)
    parser.add_argument("--timeout", type=float, default=10)
    parser.add_argument("--dry-run", action="store_true")
    args = parser.parse_args()

    try:
        seed(Api(args.base_url, args.timeout, args.dry_run))
    except (RuntimeError, KeyError) as error:
        print(f"Error: {error}", file=sys.stderr)
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
