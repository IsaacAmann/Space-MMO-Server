extends Button

var chatWindow
# Called when the node enters the scene tree for the first time.
func _ready():
	chatWindow = get_node("../ChatContainer")
	self.pressed.connect(on_pressed)



# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
	
func on_pressed():
	print("toggled Chat")
	chatWindow.set_visible(!chatWindow.visible)
	self.release_focus()
	
