extends Node2D

# Load the asteroid scene or prefab
@export var asteroid_scene: PackedScene
@export var asteroid_size: float = 50.0 

func _ready():
	spawn_asteroids(400, 3500, 100)
	spawn_asteroids(4000, 8000, 1000)

func spawn_asteroids(number_of_asteroids: int, ring_radius: float, radius_variance: float):
	var positions = []
	for i in range(number_of_asteroids):
		var position: Vector2
		var overlap = true
		while overlap:
			var angle = deg_to_rad(randf() * 360.0) # Random angle in radians
			var radius = ring_radius + randf_range(-radius_variance, radius_variance) # Random radius with variance
			position = Vector2(cos(angle), sin(angle)) * radius
			
			overlap = false
			for existing_position in positions:
				if position.distance_to(existing_position) < asteroid_size:
					overlap = true
					break
		
		positions.append(position)
		
		var asteroid = asteroid_scene.instantiate()
		asteroid.position = position
		asteroid.rotation = randf() * PI * 2 # Random rotation in radians
		add_child(asteroid)
