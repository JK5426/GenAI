import { useState } from "react";
import { Chatbot } from "supersimpledev";
import SpinnerIcon from "../assets/loading-spinner.gif";
import "./ChatInput.css";

function ChatInput({ setNewChatMessage }) {
  const [inputMessage, setInputMessage] = useState("");
  const [isLoading, setLoading] = useState(false);
  async function setMessage() {
    if (isLoading || !inputMessage.trim()) return;
    setLoading(true);
    const usermessage = [
      {
        message: inputMessage,
        sender: "user",
        id: crypto.randomUUID(),
      },
      {
        message: <img src={SpinnerIcon} className="loading-spinner" />,
        sender: "robot",
        id: crypto.randomUUID(),
      },
    ];
    setNewChatMessage((prev) => [...prev, ...usermessage]);
    setInputMessage("");
    const response = await Chatbot.getResponseAsync(inputMessage);
    if (response) {
      const botMessage = {
        message: response,
        sender: "robot",
        id: crypto.randomUUID(),
      };
      setNewChatMessage((prev) => [...prev.slice(0, -1), botMessage]);
    }
    setLoading(false);
  }
  function handleKeyPress(event) {
    if (!isLoading && event.key === "Enter") {
      setMessage();
    }
  }
  return (
    <div className="chat-input-container">
      <input
        placeholder="Send a message to Chatbot"
        size="30"
        onChange={(event) => setInputMessage(event.target.value)}
        value={inputMessage}
        onKeyDown={handleKeyPress}
        className="chat-input"
      />
      <button onClick={setMessage} disabled={isLoading} className="send-button">
        Send
      </button>
    </div>
  );
}

export default ChatInput;
