extends Node

var webSocketHandler
# Called when the node enters the scene tree for the first time.
func _ready():
	webSocketHandler = get_node("../WebSocketHandler")
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	#sendChatMessage(1, "Hello from godot")
	pass

func handleChatMessage(message):
	var currentIndex = 1
	
	var channelID
	
	var currentSlice = message.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	channelID = currentSlice.decode_s32(0)
	currentIndex += 4
	
	var messageSize = message.size() - currentIndex
	var chatSection = message.slice(currentIndex, message.size())
	
	var messageString = Marshalls.base64_to_raw(chatSection.get_string_from_ascii()).get_string_from_ascii()
	print(messageString)

func sendChatMessage(channel, message):
	var packet = PackedByteArray()
	packet.resize(1)
	packet.encode_u8(0, 0x9)
	
	var valueArray = PackedByteArray()
	valueArray.resize(4)
	valueArray.encode_s32(0, channel)
	valueArray.reverse()
	packet.append_array(valueArray)
	
	print("TEST: " + Marshalls.utf8_to_base64(message))
	packet.append_array(Marshalls.utf8_to_base64(message).to_ascii_buffer())
	
	webSocketHandler.sendMessage(packet)
	pass
