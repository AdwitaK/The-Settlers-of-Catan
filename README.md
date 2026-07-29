# The Settlers of Catan

A Java implementation of the core Settlers of Catan ruleset, built as a team project for a software design course.
The focus of the project was to apply sound OOP design, including SOLID and GRASP principles along with targeted design patterns to manage game state, plater actions and turn logic cleanly.

## Architecture
Our implementation is organized around three patterns chosen to keep game logic decoupled and testable:
- Observer: board and game-state changes are broadcast to listeners instead of polling, so all components stay in sync without being tightly coupled
- Command: player actions are encapsulated as command objects, making it easy to validate a move before executing it, and allow undo/redo actions
- Facade: a single entry point to coordinate the underlying subsystems so the user can interact with one clean interface instead of having to manage internals directly   

UML class diagrams for each assignment phase are in ```/docs```.

## Setup Instructions
1. Clone this repository
    ```bash
    git clone https://github.com/AdwitaK/The-Settlers-of-Catan
    cd visualizer
    ```
2. Create and activate a Python virtual environment
    ```bash
    python -m venv .venv
    .venv\Scripts\activate
   ```

3. Install dependencies
    ```bash
    pip install -r requirements.txt
    cd catanatron
    pip install -e ".[web,gym,dev]"
   ```
## Testing and Quality
Unit tests cover the core logic using JUnit. Static analysis was run using SonarQube.

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=AdwitaK_The-Settlers-of-Catan)](https://sonarcloud.io/summary/new_code?id=AdwitaK_The-Settlers-of-Catan)