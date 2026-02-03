import { useState } from "react";
import ChatInput from "./components/ChatInput";
import { ChatMessages } from "./components/ChatMessages";
import "./App.css";

function App() {
  const [chatMessage, setNewChatMessage] = useState([]);
  return (
    <div className="app-container">
      {chatMessage.length == 0 && (
        <p className="welcome-message">
          Welcome to the chatbot project! Send a message using the textbox
          below.
        </p>
      )}
      <ChatMessages chatMessage={chatMessage} />
      <ChatInput setNewChatMessage={setNewChatMessage} />
    </div>
  );
}

export default App;
