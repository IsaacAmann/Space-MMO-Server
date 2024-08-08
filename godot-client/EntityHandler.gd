extends Node2D

@export var webSocketHandler: Node2D

@export var entityDictionary: Dictionary

var adminOrb = preload("res://Ships/Debug/AdminOrb/AdminOrb.tscn")
var pipeDutch = preload("res://Ships/pipeDutch/pipeDutch.tscn")


var playerShip = null
func getPlayerShip():
	return playerShip
	
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
	var positionX
	var positionY
	var rotation
	var velocityX
	var velocityY
	
	#skip message type label
	var currentIndex = 1

	entityId = message.decode_s32(currentIndex)
	currentIndex += 4
	
	entityType = message.decode_s8(currentIndex)
	currentIndex += 1
	
	positionX = message.decode_float(currentIndex)
	currentIndex += 4
	
	positionY = message.decode_float(currentIndex)
	currentIndex += 4
	
	rotation = message.decode_float(currentIndex)
	currentIndex += 4
	
	velocityX = message.decode_float(currentIndex)
	currentIndex += 4
	
	velocityY = message.decode_float(currentIndex)
	currentIndex += 4
	
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
		var json = JSON.new()
		var error = json.parse(jsonString)
		if error == OK:
			var data = json.data
			var entityPath = data.godotScenePath
			var entityScene
			
			if entityPath != null:
				entityScene = load(entityPath)
			if entityScene == null:
				entityScene = load("res://Entities/error.tscn")
			newEntity = entityScene.instantiate()
			if("username" in data && data.username != null && "nameLabel" in newEntity):
				newEntity.nameLabel = data.username
				var username = JavaScriptBridge.eval("sessionStorage.getItem('username')")
				if(username == newEntity.nameLabel):
					playerShip = newEntity
			if("externalModules" in data):
				for i in range(data.externalModules.size()):
					var moduleScene = load(data.externalModules[i].scenePath)

					if(moduleScene != null && newEntity.get_node("./externalModuleContainer") != null):
						var module = moduleScene.instantiate()
						newEntity.get_node("./externalModuleContainer").add_child(module)
					else:
						print("Error adding module")
					pass
			
		else:
			print("JSON error: ", json.get_error_message(), " in ", jsonString, " at line ", json.get_error_line())

	else:
		#Handle binary format
		pass
	#Create entity object
	if newEntity != null:
		get_tree().root.add_child(newEntity)
		entityDictionary[entityId] = newEntity
		if("position" in newEntity):
			newEntity.position = Vector2(positionX, positionY)
		if("rotation" in newEntity):	
			newEntity.rotation = rotation
		if("velocity" in newEntity):	
			newEntity.velocity = Vector2(velocityX, velocityY)
	
	
func handleEntityDelete(message: PackedByteArray):
	#parse message
	var entityId
	
	var currentIndex = 1

	entityId = message.decode_s32(currentIndex)
	
	#get reference from dictionary
	var entity = entityDictionary.get(entityId)
	if(entity != null):
		#remove from tree and from dictionary
		entity.queue_free()
		entityDictionary.erase(entityId)
	

func handleEntityUpdate(message: PackedByteArray):
	#parse message
	var entityId
	var positionX
	var positionY
	var velocityX
	var velocityY
	var health
	var rotation
	var rotationalVelocity
	
	var currentIndex = 1

	entityId = message.decode_s32(currentIndex)
	currentIndex += 4
	
	positionX = message.decode_float(currentIndex)
	currentIndex += 4

	positionY = message.decode_float(currentIndex)
	currentIndex += 4
	
	velocityX = message.decode_float(currentIndex)
	currentIndex += 4
	
	velocityY = message.decode_float(currentIndex)
	currentIndex += 4
	
	health = message.decode_s32(currentIndex)
	currentIndex += 4
	
	rotation = message.decode_float(currentIndex)
	currentIndex += 4
	
	rotationalVelocity = message.decode_float(currentIndex)
	currentIndex += 4
	
	#modify entity
	var entity = entityDictionary.get(entityId)
	if(entity != null):
		if("lerpPosition" in entity):
			entity.lerpPosition = Vector2(positionX, positionY)
		else:
			entity.position = Vector2(positionX, positionY)
			
		if("velocity" in entity):
			entity.velocity = Vector2(velocityX, velocityY)
			print("velocity: " + str(velocityX))
		if("angularVelocity" in entity):
			entity.angularVelocity = rotationalVelocity
		#entity.spriteRotation = rotation
		entity.rotation = rotation
