extends Control

@export var target_node: Node
@export var back_button: Button

func _on_open_pressed():
	if target_node:
		target_node.visible = not target_node.visible
		back_button.visible = not back_button.visible

func _on_close_pressed():
	if target_node:
		target_node.visible = not target_node.visible
		back_button.visible = not back_button.visible

