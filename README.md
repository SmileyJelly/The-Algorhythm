# 🧩 Console Sudoku Game (Java)

Welcome to the Console Sudoku Game — a simple but functional Sudoku puzzle game you can play right in your terminal!

This project features a complete Sudoku puzzle generator and solver built in Java, allowing users to play manually or auto-solve the puzzle at any time.

---

## 🎮 How to Play

- The game generates a **Sudoku puzzle** based on difficulty.
- You can **enter a row, column, and number** to place a value in the grid.
- Type **`s`** at any time to automatically solve the puzzle.
- The game checks for valid moves and won’t allow duplicates based on standard Sudoku rules.

---

## 📌 Features

- ✅ Fully functional 9x9 Sudoku board
- 🎲 Random puzzle generation with adjustable difficulty
- 🧠 Built-in backtracking algorithm to solve puzzles
- 📋 Console-based user input and game loop
- 🛡 Input validation and feedback messages

---

## 🛠 Tech Stack

- **Language:** Java  
- **Tools Used:** Scanner, Random, ArrayList, Collections

---

## 🧪 Difficulty Levels (in code)

You can adjust puzzle difficulty by changing the value in `generatePuzzle(difficulty)`:
```java
board.generatePuzzle(1); // 1 = Easy, 2 = Medium, 3 = Hard
