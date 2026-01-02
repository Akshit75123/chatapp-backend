const socket = io("http://localhost:8080"); // Connect to the server

// Listen for messages from the server
socket.on("message", (message) => {
    const chatBox = document.getElementById("chatBox");
    const messageElement = document.createElement("div");
    messageElement.textContent = message;
    chatBox.appendChild(messageElement);
    chatBox.scrollTop = chatBox.scrollHeight; // Scroll to the bottom
});

// Send a message to the server
function sendMessage() {
    const message = document.getElementById("messageInput").value;
    socket.emit("sendMessage", message); // Emit message to the server
    document.getElementById("messageInput").value = ""; // Clear input field
}
