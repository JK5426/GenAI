import { useAutoScroll } from "../hooks/customHooks";
import { ChatMessage } from "./ChatMessage";
import "./ChatMessages.css";

export function ChatMessages({ chatMessage }) {
  const chatMessagesRef = useAutoScroll(chatMessage);
  return (
    <div className="chat-message-container" ref={chatMessagesRef}>
      {chatMessage &&
        chatMessage.map((value) => <ChatMessage {...value} key={value?.id} />)}
    </div>
  );
}
