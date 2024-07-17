extends Area2D

# Called when the node enters the scene tree for the first time.
func _ready():
	# Get all CollisionShape2D nodes that are children of this node
	var collision_shapes = []
	for child in get_children():
		if child is CollisionShape2D:
			collision_shapes.append(child)
	
	for i in range(collision_shapes.size()):
		collision_shapes[i].visible = false
	
	var random_index = randi() % collision_shapes.size()
	collision_shapes[random_index].visible = true
