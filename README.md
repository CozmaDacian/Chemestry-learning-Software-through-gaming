# Chemestry-learning-Software-through-gaming

Chemistry-learning-Software-through-Gaming

Chemistry-learning-Software-through-Gaming is an educational software project designed to transform the study of chemistry (and optionally biology) into a highly interactive and intuitive gaming experience. The software was created as an alternative to traditional study methods, especially for students preparing for demanding exams such as medical school admissions.

The core concept is simple: players learn chemistry while actively playing. The game integrates theoretical content, quizzes, combat sequences, and exploration mechanics to improve retention, motivation, and engagement. Theory is loaded from PowerPoint (.ppt) files, while question banks are stored as .txt files for easy editing.

The application is implemented primarily in Java, without the use of external game-development libraries. All systems—including animation, rendering, collision detection, serialization, and UI—are coded from scratch. A C# component is included for editing and managing quiz files.

Educational Motivation

Many students find chemistry and biology difficult because the subjects involve abstract concepts that are hard to visualize. This software aims to provide an interactive learning environment that:
- Encourages problem-solving,
- Reinforces theoretical content through gameplay,
- Reduces stress through gamification,
- Supports differentiated learning by allowing instructors to modify content dynamically.

Core Gameplay Concept

The player navigates a 2D world, collects items, engages in combat with robotic enemies, and restores in-game resources by answering chemistry or biology questions correctly. The gameplay includes:
- A full playable level with movement, collisions, item collection, and NPC interactions.
- Enemy characters with multiple animation states and AI-driven attack patterns.
- A skill tree system for character progression.
- An inventory system with consumable items.
- A theory book system using images converted from PowerPoint slides.
- A combat system directly influenced by the player’s knowledge through quiz-based energy recovery.

Technical Architecture

The application is organized into multiple Java packages:

1. actiune_joc – Controls game states, such as transitions between playing, combat, inventory, and theory reading.
2. main_game – Contains the character, enemies, game loop, combat HUD, and UI elements.
3. nivel – Loads level data from image-based tile maps and initializes textures, enemies, and objects.
4. stats – Stores attributes for enemies and the player.
5. menu – Contains the start menu, pause menu, and save/load system using serialization.
6. utilitare – Includes constants, helper functions for items and animations, and logic for processing object effects.

Gameplay Systems

Checkpoint System
Stores player position, enemy states, collected items, armor color, and statistics using serialization.

Combat System
Turn-based combat system with:
- Melee and ranged enemy attacks,
- Knock-back mechanics,
- Player abilities with cooldowns and energy use,
- Ability descriptions and interactive interface elements.

Quiz System
Used during combat to allow recovery of energy:
- Loads .txt questions and answers,
- Supports multiple correct answers using a queue structure,
- Displays explanations after submission,
- Center-aligns text dynamically based on line width.

Inventory System
Handles:
- Item stacking,
- Consumable items,
- Object descriptions with dynamic text wrapping,
- Item effects applied through a key-based indexing system.

Book / Theory System
Converts PowerPoint slides into PNG images using Apache POI, making it easy for instructors to update theory content.

Conclusion

This software represents a complete game-based learning platform, merging educational theory with interactive gameplay. It promotes self-evaluation, strengthens understanding through repetition, and provides a more engaging learning experience compared to traditional study methods.

By integrating quizzes, theory, and combat mechanics, the system encourages active participation and improves students’ motivation to study chemistry and biology.

link for download and a demo
https://drive.google.com/drive/u/0/folders/1699EO_I1_8gNF3U2FLAEOvqJB2evct3s


No particular Libraries were used
for the game pyshics all is done from scratch


This software belongs to Cozma Dacian 
