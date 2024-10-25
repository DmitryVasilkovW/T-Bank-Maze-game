# User Interaction with MazeLauncher

When using the `MazeLauncherImpl` class, users will interact through the console, providing specific inputs based on prompts. Below is a detailed guide on what the user needs to enter and what they can expect to receive in response.

## Input Prompts and Expected User Responses

1. **Should we build a maze with narrow passageways?**
    - **User Input:** `yes` / `y` / `no` / `n`
    - **Expected Response:** The maze will be generated according to the chosen option (with or without narrow passageways).

2. **Can the hero fit into narrow passageways?**
    - **User Input:** `yes` / `y` / `no` / `n`
    - **Expected Response:** The solver will adapt its strategy based on whether the hero can fit into narrow passageways.

3. **Do we need to create more dead ends and long corridors?**
    - **User Input:** `yes` / `y` / `no` / `n`
    - **Expected Response:** The maze generator will consider the user's preference for dead ends and corridors when generating the maze.

4. **Does the hero need to pay attention to surfaces?**
    - **User Input:** `yes` / `y` / `no` / `n`
    - **Expected Response:** The selected solver will take surfaces into account while searching for a path.

5. **Enter point for start. Format: row column (1 1)**
    - **User Input:** `row column` (e.g., `1 1`)
    - **Expected Response:** The starting point of the maze will be set. If the input is invalid, the user will be prompted to re-enter.

6. **Enter point for end. Format: row column (13 13)**
    - **User Input:** `row column` (e.g., `13 13`)
    - **Expected Response:** The end point of the maze will be established. Invalid input will lead to a re-prompt.

7. **Enter height and width for maze. Format: height width (15 15)**
    - **User Input:** `height width` (e.g., `15 15`)
    - **Expected Response:** The maze dimensions will be set. Users will receive a warning if the input does not meet the minimum requirements (e.g., must be at least `3 x 3`).

## Output Messages and Results

1. **Maze Generation**
    - Upon successful generation of the maze, the user will see a visual representation of the maze printed to the console.

2. **Path Finding**
    - If a path is found from the start to the end points, the console will display:
      ```
      Path found:
      ```
    - This will be followed by a visual representation of the maze with the path highlighted.

3. **No Path Found**
    - If the solver fails to find a path, the console will display:
      ```
      No path found.
      ```

## Example Interaction

1. **Prompt:** "Should we build a maze with narrow passageways?"
    - **User Input:** `yes`
    - **Response:** Maze will be generated with narrow passageways. This means that it will be possible to move diagonally during generation.
   - **User Input** `no`
   - **Response:** The aisle generator won't be able to walk diagonally.

2. **Prompt:** "Can the hero fit into narrow passageways?"
    - **User Input:** `yes`
    - **Response:** Then the hero will be able to pass through narrow passages. That is, he can move diagonally.
   - **User Input** `no`
   - **Response:** The hero won't be able to walk diagonally.

3. **Prompt:** "Do we need to create more dead ends and long corridors?"
    - **User Input:** `yes`
    - **Response:** Maze will be generated with narrow passageways by DFS.
   - **User Input** `no`
   - **Response:** The walkthroughes will be more randomized and challenging by Prime.

4. **Prompt:** "Enter point for start. Format: row column (1 1)"
    - **User Input:** `1 1`
    - **Response:** Starting point is set at (1, 1).

5. **Prompt:** "Enter point for end. Format: row column (13 13)"
    - **User Input:** `13 13`
    - **Response:** Starting point is set at (13, 13).

6. **Prompt:** "Enter height and width for maze. Format: height width (15 15)"
    - **User Input:** `15 15`
    - **Response:** Maze dimensions set to 15 rows and 15 columns.

7. **Prompt:** "Does the hero need to pay attention to surfaces?"
    - **User Input:** `yes`
    - **Response:** The hero will collect useful items and bypass the bad areas With A*.
    - **User Input** `no`
    - **Response:** The hero will not collect useful items and bypass the bad areas With BFS.
