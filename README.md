# FLA-App (Food Label Analyzer)

## Description

FLA-App is an Android application designed to extract and analyze ingredients from food label images. It helps users understand the health impact of various food components by providing an assessment based on the scanned label.

**This is the frontend mobile application. The backend service for this application can be found at [thisisharshavardhan/Label-scanner](https://github.com/thisisharshavardhan/Label-scanner).**

## Screenshots

Here are some screenshots of the application:

<p align="center">
  <img src="WhatsApp Image 2025-04-18 at 8.02.14 PM.jpeg" alt="FLA-App Screenshot 2" width="300"/>
  <img src="WhatsApp Image 2025-04-18 at 8.02.13 PM.jpeg" alt="FLA-App Screenshot 1" width="300"/>
</p>

*(Please replace these with more descriptive alt text if needed, or if these images are not representative of the final app, update them accordingly.)*

## Features

*   **Image-based Ingredient Extraction:** Utilizes the device camera to capture images of food labels.
*   **Ingredient Analysis:** Parses the extracted ingredients to provide insights into their health implications.
*   **Health Impact Assessment:** Offers an evaluation of the food product based on its ingredient list.

## Technologies Used

*   **Kotlin:** The primary programming language for the Android application.
*   **Android SDK:** For building the native Android application.
*   **Gradle:** For project build automation.

## Project Structure

The repository is structured as a standard Android Gradle project:

```
FLA-App/
├── .gitignore        # Specifies intentionally untracked files that Git should ignore
├── .idea/            # IntelliJ IDEA project files (can be ignored by other IDEs)
├── app/              # Main application module (source code, resources, etc.)
│   ├── build.gradle.kts # Build script for the app module
│   └── src/
│       ├── main/
│       │   ├── java/   # Kotlin/Java source code
│       │   ├── res/    # Application resources (layouts, drawables, strings, etc.)
│       │   └── AndroidManifest.xml # Application manifest file
├── build.gradle.kts  # Top-level build file for the project
├── gradle.properties # Project-wide Gradle settings
├── gradle/           # Gradle wrapper files
├── gradlew           # Gradle wrapper script for Unix-like systems
├── gradlew.bat       # Gradle wrapper script for Windows
├── settings.gradle.kts # Defines project structure for Gradle
├── WhatsApp Image 2025-04-18 at 8.02.13 PM.jpeg # Screenshot 1
├── WhatsApp Image 2025-04-18 at 8.02.14 PM.jpeg # Screenshot 2
└── README.md         # This file
```
*(Note: Some less relevant files/folders might be omitted for brevity.)*

## Getting Started

### Prerequisites

*   Android Studio (latest stable version recommended)
*   Kotlin plugin for Android Studio
*   Access to a running instance of the [Label-scanner backend service](https://github.com/thisisharshavardhan/Label-scanner). (You'll likely need to configure the app to point to your backend's URL).

### Building and Running

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/thisisharshavardhan/FLA-App.git
    ```
2.  **Set up the backend:**
    *   Clone and run the [thisisharshavardhan/Label-scanner](https://github.com/thisisharshavardhan/Label-scanner) project as per its README instructions.
    *   Ensure the Android app knows the URL of your running backend (this might involve changing a configuration file or a constant within the Android app's code).
3.  **Open in Android Studio:**
    *   Launch Android Studio.
    *   Select "Open an existing Android Studio project".
    *   Navigate to the cloned `FLA-App` directory and select it.
4.  **Sync Project with Gradle Files:**
    *   Android Studio should automatically sync the project. If not, click on "Sync Project with Gradle Files".
5.  **Run the application:**
    *   Select an emulator or connect a physical Android device.
    *   Click the "Run 'app'" button.

## Contributing

Contributions are welcome! If you'd like to contribute, please follow these steps:

1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature/your-feature-name`).
3.  Make your changes.
4.  Commit your changes (`git commit -m 'Add some feature'`).
5.  Push to the branch (`git push origin feature/your-feature-name`).
6.  Open a Pull Request.

Please ensure your code follows the project's coding style and includes appropriate tests.

## License

This project does not currently have a license. Please consider adding one (e.g., MIT, Apache 2.0) to define how others can use and contribute to your project. You can add a `LICENSE` file to the root of your repository.

---

*This README was auto-generated and then modified. Please review and update as needed.*
*You might want to add more specific details about the app's functionality, setup instructions for any specific APIs or libraries used.*
