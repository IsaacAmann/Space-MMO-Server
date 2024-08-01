extends AnimatedSprite2D

@export var animation_to_play: String

func _ready():
	# Play the desired animation set in the inspector
	if animation_to_play != "":
		play(animation_to_play)
	else:
		print("No animation name set in the inspector.")
