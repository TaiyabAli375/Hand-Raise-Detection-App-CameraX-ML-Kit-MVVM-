# **Hand Raise Detection App (CameraX + ML Kit + MVVM)**

A real-time hand-raise detection Android app built using CameraX, ML Kit Pose Detection, and MVVM architecture.
When the user raises their hand, the app detects it and responds with Text-to-Speech (TTS).

### 1. Libraries Used
**CameraX**

Used for real-time camera preview and frame analysis. CameraX provides:
Live preview (Preview)
Frame analysis for ML Kit (ImageAnalysis)
Automatic handling of rotation

**ML Kit Pose Detection**

Library: implementation "com.google.mlkit:pose-detection:18.0.0"
Used to detect pose and giving landmarks of body parts.
The app uses STREAM_MODE for continuous real-time hand detection.

**TextToSpeech (TTS)**

Android's TTS engine is used for:
“Hand detected, how can I help you?” triggered when the hand first goes up.

### 2. Hand Raise Detection Logic

ML Kit provides body landmark coordinates.
The logic is:
First get Right Shoulder, Left Shoulder, Right Index Finger and Left Index Finger landmarks.
Then find highest shoulder between right shoulder and left shoulder, also find highest index finger between right index finger and left index finger.
Now check if highest index finger has lower value(y) than highest shoulder means index finger is higher than shoulder, it means hand is raised.

### 3. MVVM Structure
**PoseRepository (Model):**
- Initializes ML Kit PoseDetector
- Applies hand-raise detection logic
- Returns result via callback
- This is the main ML logic layer.

**PoseViewModel (ViewModel):**
- Exposes LiveData<Boolean> for UI
- Calls repository to process each frame
- Posts the detection result
- Keeps UI logic separated from ML logic.

**MainActivity (View):**
- Starts CameraX
- Collects frames and sends them to ViewModel
- Observes isHandRaise LiveData
- Updates UI text color & status
- Plays Text-to-Speech when hand is raised
