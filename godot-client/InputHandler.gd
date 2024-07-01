extends Node2D

var webSocketHandler

var wDown = false
var aDown = false
var sDown = false
var dDown = false
var spaceDown = false
var qDown = false
var eDown = false
var fDown = false
#indicates that input has changed client side and server should be notified
var inputChanged = false

# Called when the node enters the scene tree for the first time.
func _ready():
	webSocketHandler = get_node("../WebSocketHandler")



# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	inputChanged = false
	#Check for user input
	#Check for W key
	if(Input.is_action_pressed("w_down")):
		if(wDown == false):
			inputChanged = true
		wDown = true
	else:
		if(wDown == true):
			inputChanged = true
		wDown = false
	#Check for A key
	if(Input.is_action_pressed("a_down")):
		if(aDown == false):
			inputChanged = true
		aDown = true
	else:
		if(aDown == true):
			inputChanged = true
		aDown = false
	#Check for S key
	if(Input.is_action_pressed("s_down")):
		if(sDown == false):
			inputChanged = true
		sDown = true
	else:
		if(sDown == true):
			inputChanged = true
		sDown = false
	#Check for D key
	if(Input.is_action_pressed("d_down")):
		if(dDown == false):
			inputChanged = true
		dDown = true
	else:
		if(dDown == true):
			inputChanged = true
		dDown = false
	#Check for Space key
	if(Input.is_action_pressed("space_down")):
		if(spaceDown == false):
			inputChanged = true
		spaceDown = true
	else:
		if(spaceDown == true):
			inputChanged = true
		spaceDown = false
	#Check for Q key
	if(Input.is_action_pressed("q_down")):
		if(qDown == false):
			inputChanged = true
		qDown = true
	else:
		if(qDown == true):
			inputChanged = true
		qDown = false
	#Check for E key
	if(Input.is_action_pressed("e_down")):
		if(eDown == false):
			inputChanged = true
		eDown = true
	else:
		if(eDown == true):
			inputChanged = true
		eDown = false
	#Check for F key
	if(Input.is_action_pressed("f_down")):
		if(fDown == false):
			inputChanged = true
		fDown = true
	else:
		if(fDown == true):
			inputChanged = true
		fDown = false
	
	#If input has changed, send input packet to server 
	if(inputChanged == true):
		var message = PackedByteArray()
		var inputByte: int = 0 
		#Flip bits for keys pressed down
		if(wDown):
			inputByte = inputByte | 0b00000001
		if(aDown):
			inputByte = inputByte | 0b00000010
		if(sDown):
			inputByte = inputByte | 0b00000100
		if(dDown):
			inputByte = inputByte | 0b00001000
		if(spaceDown):
			inputByte = inputByte | 0b00010000
		if(qDown):
			inputByte = inputByte | 0b00100000
		if(eDown):
			inputByte = inputByte | 0b01000000
		if(fDown):
			inputByte = inputByte | 0b10000000
		
		message.resize(2)
		message.encode_u8(0, 0x6)
		message.encode_u8(1, inputByte)
		print("SIZE: " + str(message.size()))
		print("Byte: " + str(inputByte))
		
		webSocketHandler.sendMessage(message)
