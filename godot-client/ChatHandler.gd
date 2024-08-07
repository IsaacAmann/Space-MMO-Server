extends Node

var webSocketHandler
var chatBox

# Called when the node enters the scene tree for the first time.
func _ready():
	webSocketHandler = get_node("../WebSocketHandler")
	chatBox = get_node("../CanvasLayer/ChatBox/ChatContainer/ChatWindow")
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	#sendChatMessage(1, "Hello from godot")
	pass

func handleChatMessage(message):
	var currentIndex = 1
	var channelID

	channelID = message.decode_s32(currentIndex)
	currentIndex += 4
	
	var messageSize = message.size() - currentIndex
	var chatSection = message.slice(currentIndex, message.size())
	
	var messageString = Marshalls.base64_to_raw(chatSection.get_string_from_ascii()).get_string_from_ascii()
	print(messageString)
	chatBox.text += "\n" + str(messageString)

func sendChatMessage(channel, message):
	var packet = PackedByteArray()
	packet.resize(5)
	packet.encode_u8(0, 0x9)
	packet.encode_s32(1, channel)
	
	print("TEST: " + Marshalls.utf8_to_base64(message))
	packet.append_array(Marshalls.utf8_to_base64(message).to_ascii_buffer())
	
	webSocketHandler.sendMessage(packet)
	pass
