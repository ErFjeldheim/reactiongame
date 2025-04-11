# ReactionGame - Reaction Time Testing Application

This JavaFX application lets users test and track their reaction time by responding to visual stimuli. The application measures the time it takes for a user to press a button or key after a red button appears on screen.

## Features

- **Fast Reaction Testing**: Test your reaction time by responding to visual cues
- **Statistics**: View your best time and average reaction time across tests
- **Session Management**: Complete customizable sessions with multiple tests
- **Historical Data**: View all previous test results in a table and graph
- **Customizable Settings**: Adjust number of tests per session and timing parameters
- **File Storage**: All results are saved between sessions

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository
2. Navigate to the project directory
3. Build the project using Maven:

```
mvn clean install
```

### Running the Application

Launch the application with:

```
mvn clean javafx:run
```

## How to Use

1. **Start a Test**: Press the "Start" button to begin a reaction test
2. **Wait for the Signal**: Wait until you see "Trykk nå!" (Press now!) and the button turns red
3. **React Quickly**: Press either the button or spacebar as quickly as possible
4. **View Results**: See your reaction time and statistics after each test
5. **Complete the Session**: Finish all tests in a session to see your average time
6. **Review History**: Click "Vis historikk" (Show history) to view your progress over time
7. **Adjust Settings**: Click "Innstillinger" (Settings) to customize your testing experience

## Project Structure

- **Model**: Core functionality classes (`ReactionTest`, `ResultManager`, etc.)
- **Controller**: JavaFX controllers managing user interaction
- **View**: FXML files defining the user interface
- **File Storage**: Persistence system for saving and loading test results

## Testing

The application includes comprehensive JUnit tests covering:

- Reaction time calculation
- Input validation
- Statistical calculations
- Random interval generation
- File I/O operations
- State management

Run tests with:

```
mvn test
```

## Implementation Details

- Implements the Model-View-Controller (MVC) architecture pattern
- Uses JavaFX for the graphical user interface
- Implements the `Playable` interface for game functionality
- Uses Java's `Preferences` API for storing user settings
- Handles file I/O for persistent storage of reaction time data

## License

This project is available as open source under the terms of the MIT License.
