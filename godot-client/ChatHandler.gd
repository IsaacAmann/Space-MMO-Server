extends Node


# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
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
