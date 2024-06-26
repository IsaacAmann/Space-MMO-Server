extends Node2D

@export var webSocketHandler: Node2D

@export var entityDictionary: Dictionary

var adminOrb = preload("res://Ships/Debug/AdminOrb/AdminOrb.tscn")
var sovietRocketOne = preload("res://Ships/SovietRocketOne/SovietRocketOne.tscn")
# Called when the node enters the scene tree for the first time.
func _ready():
	webSocketHandler.entity_update_message.connect(handleEntityUpdate)
	webSocketHandler.new_entity_message.connect(handleNewEntity)
	webSocketHandler.entity_delete_message.connect(handleEntityDelete)
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass

func handleNewEntity(message: PackedByteArray):
	print("creating new entity")
	#parse message
	var entityId
	var entityType
	var entityDataLength
	
	#skip message type label
	var currentIndex = 1
	entityId = message.decode_s32(currentIndex)
	currentIndex += 4
	entityType = message.decode_s8(currentIndex)
	currentIndex += 1
	
	print("entityID: " + str(entityId))
	print("entityType: " + str(entityType))
	
	var dataLength = message.size() - currentIndex
	print("data section length: " + str(dataLength))
	var dataSection = message.slice(currentIndex, message.size())
	
	var newEntity
	#if JSON type, get JSON string
	if entityType == 0:
		var jsonString = Marshalls.base64_to_raw(dataSection.get_string_from_ascii()).get_string_from_ascii();
		
		print("Json: " + jsonString)
		
		newEntity = sovietRocketOne.instantiate()
		pass
	else:
		#Handle binary format
		pass
	#Create entity object
	if newEntity != null:
		get_tree().root.add_child(newEntity)
		entityDictionary[entityId] = newEntity
	
	
func handleEntityDelete(message: PackedByteArray):
	#parse message
	var entityId
	var currentIndex = 1
	
	entityId = message.decode_s32(currentIndex)
	#get reference from dictionary
	var entity = entityDictionary[entityId]
	#remove from tree and from dictionary
	entity.queue_free()
	entityDictionary.erase(entityId)
	pass

func handleEntityUpdate(message: PackedByteArray):
	#parse message
	var entityId
	var positionX
	var positionY
	var velocityX
	var velocityY
	var health
	
	var currentIndex = 1
	entityId = message.decode_s32(currentIndex)
	currentIndex += 4
	
	positionX = message.decode_float(currentIndex)
	currentIndex += 4
	
	positionY = message.decode_float(currentIndex)
	currentIndex += 4
	
	#java encodes in big endian, need to reverse the floats
	var velocitySlice = message.slice(currentIndex, currentIndex + 4)
	velocitySlice.reverse()
	velocityX = velocitySlice.decode_float(0)
	currentIndex += 4
	
	velocityY = message.decode_float(currentIndex)
	currentIndex += 4
	
	health = message.decode_s32(currentIndex)
	currentIndex += 4
	
	#modify entity
	var entity = entityDictionary[entityId]
	#entity.position = Vector2(positionX, positionY)
	entity.velocity = Vector2(velocityX, velocityY)
	print("velocityx: " + str(velocityX))


