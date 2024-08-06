extends LineEdit

var chatHandler
# Called when the node enters the scene tree for the first time.
func _ready():
	chatHandler = get_node("../../../../ChatHandler")
	self.text_submitted.connect(on_text_submit)
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func on_text_submit(text: String):
	print("submitting")
	chatHandler.sendChatMessage(0, text)
	self.text = ""
	self.release_focus()
