GDPC                �                                                                      )   T   res://.godot/exported/133200997/export-5c5b3d93a3b232a2de49b17fcd982c47-BaseShip.scn      '      ��h�SX4��5���    \   res://.godot/exported/133200997/export-81ebc570ea2082422c22a7ebc780f341-SovietRocketOne.scn P      z      ���}r۷��ӪF���/    X   res://.godot/exported/133200997/export-945b6b44938192d43dbc1021d519b07c-MiningDrill.scn �      �      ��#� �Igf�    X   res://.godot/exported/133200997/export-a00ca0e08a1ac5e46d330a7c1b8d7acb-MainScene.scn   ``      6      5^�y,��J��A���d5    \   res://.godot/exported/133200997/export-b6d946d4138cc1c6d0c9dfbaeef38f77-InternalModule.scn  �-      n      �Ľ�C$�FC<3�T�    X   res://.godot/exported/133200997/export-c4d36427f61dc3c33acb0756fa918854-BaseWeapon.scn  �      �      �;���M Q%�_�T$    \   res://.godot/exported/133200997/export-c7b3668845cd4ce49ae464e4e1f8f516-BaseProjectile.scn          �      �⺂*~��;��}�n    \   res://.godot/exported/133200997/export-d5f8655d686f4268a103cd62bf5f3dac-ExternalModule.scn  �)      �      e�&�c�>��s��    T   res://.godot/exported/133200997/export-f917fe205a6922e255b6f4deea94106e-AdminOrb.scn�      �      �E�r�NLд��GQ��    ,   res://.godot/global_script_class_cache.cfg  pw            x���lf�ҵ��    H   res://.godot/imported/AdminOrb.png-572405b0741027976de96989c70a77b6.ctex@
      �      ���c�NG7�7+��#��    X   res://.godot/imported/SovietRocketOneAirframe.png-e75d5c33df5cbee3f6b0ebc39f376efc.ctex �#      �      ��z�ꌺX�P��ޝh    D   res://.godot/imported/icon.svg-218a8f2b3041327d8a5756f3a245f83b.ctex0E            ：Qt�E�cO���    L   res://.godot/imported/miningDrill.png-1d4d599d25e2fa7d146dab4cbaacfee2.ctex �            '�����o��ꨚ�*U    H   res://.godot/imported/purple.png-993a3fae7325db95475822da4ec84ea6.ctex  �d      ^       ,x�[��Lr������f�       res://.godot/uid_cache.bin  P|      �      �̲���[e�)dc��       res://Camera2D.gd   00      �      �"l< {�f�z��e    8   res://Entities/BaseProjectile/BaseProjectile.tscn.remap �s      k       � �-�:o�N�OV�(       res://EntityHandler.gd  �2      9      ���R�L^����C]       res://InputHandler.gd    S      5      ���ӕ������V       res://MainScene.tscn.remap   w      f       :� ��;	Y�i��Λ        res://Ships/BaseShip/BaseShip.gd�      ^      !��_>T��H��L^    (   res://Ships/BaseShip/BaseShip.tscn.remap�s      e       Q�"$2� ����D�    0   res://Ships/Debug/AdminOrb/AdminOrb.png.import        �       ��L󿗅�{���_    0   res://Ships/Debug/AdminOrb/AdminOrb.tscn.remap  `t      e       E�qp_w��2�
h <        res://Ships/ExternalModule.gd   �(      9      gAӕ�E:���8��x�    (   res://Ships/ExternalModule.tscn.remap    v      k       �m�5��S���d��j�        res://Ships/InternalModule.gd   �,      ;      �R[2��l��¼�`    (   res://Ships/InternalModule.tscn.remap   �v      k       �Ȃf�_��>`I�݆�    8   res://Ships/Modules/BaseModules/BaseWeapon/BaseWeapon.gd�      =      7{��ߠO�X�v� |    @   res://Ships/Modules/BaseModules/BaseWeapon/BaseWeapon.tscn.remap�t      g       Wbu�.�<$���    L   res://Ships/Modules/ExternalModules/Tools/MiningDrill/MiningDrill.tscn.remap@u      h       ������Yj�2�C�    L   res://Ships/Modules/ExternalModules/Tools/MiningDrill/miningDrill.png.import�      �       4`2L���H�,�(]�B    0   res://Ships/SovietRocketOne/SovietRocketOne.gd  �      �       �X�k^�(��G����    8   res://Ships/SovietRocketOne/SovietRocketOne.tscn.remap  �u      l       hiY
G��h�\�@v    @   res://Ships/SovietRocketOne/SovietRocketOneAirframe.png.import  �'      �       ����z��ȝ!����       res://WebSocketHandler.gd   �e      �      
�[?�^ ���ۂ�       res://icon.svg  �x      �      k����X3Y���f       res://icon.svg.import   PR      �       Xhu�}�@���5�%��       res://project.binary@      �      x�]�+F�X�f�b�       res://purple.png.import  e      �       ;� ����c=�ũR���                RSRC                    PackedScene            ��������                                                  resource_local_to_scene    resource_name 	   _bundled    script           local://PackedScene_p0woa �          PackedScene          	         names "         BaseProjectile    Node2D 	   Sprite2D    Area2D    CollisionShape2D    	   variants              node_count             nodes        ��������       ����                      ����                      ����                     ����              conn_count              conns               node_paths              editable_instances              version             RSRC  extends CharacterBody2D

var ExternalModule = preload("res://Ships/ExternalModule.gd")
var InternalModule = preload("res://Ships/InternalModule.gd")

var internalModules: Array[InternalModule] = []
var externalModules: Array[ExternalModule] = []

var angularVelocity: float = 0


@export var entityID: int
@export var spriteRotation: float
var sprite
var externalModuleContainer

# Called when the node enters the scene tree for the first time.
func _ready():
	
	externalModuleContainer = get_node("./externalModuleContainer")
	#get internal modules
	sprite = get_node("./Sprite2D")
	#get external modules
	
	
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	
	position += velocity * delta
	#sprite.rotation = spriteRotation
	#sprite.rotation += angularVelocity * delta
	#rotation = spriteRotation
	rotation += angularVelocity * delta
	#Modify external Modules
	var moduleContainer = get_node("./externalModuleContainer")
	var modules = moduleContainer.get_children()
	for i in range(modules.size()):
		modules[i].rotation = 0



  RSRC                    PackedScene            ��������                                                  resource_local_to_scene    resource_name 	   _bundled    script       Script !   res://Ships/BaseShip/BaseShip.gd ��������      local://PackedScene_nree7          PackedScene          	         names "      	   BaseShip    script    CharacterBody2D 	   Sprite2D    CollisionPolygon2D    externalModuleContainers    Node2D    	   variants                       node_count             nodes        ��������       ����                            ����                      ����                      ����              conn_count              conns               node_paths              editable_instances              version             RSRC         GST2   I   J      ����               I J        �  RIFF�  WEBPVP8L�  /H@���mۘ?�Ys����ۮ�m�&;���" ޹j�,눶N� ĉq ��
�8�pQ		�a.f8 �m����QR�֓i�qd�H�8��)aj*��J&Z��n��_��WRf�5����c��;u��6,�Z�
��j1�7s`�a���e�mfX��nym��p8!���2R�d�vN��K>��z�NX��)_��v{�O���#����>����\�7Fn��!�}b�� X�aBG4�'<�-h�g��G�G˝�b</@�|,vα�M���Ϙh�׷�;��(/����M��&�>ʅC�l��6t��ʖ� �=�	b*+��d�!�wy)���9��Z�����A��g�dF�+�+J�V��ՔJ�x'e�W�p0��CvN����ńd6/���xѴ���4ꠒ�٤����9���v�`8z��䋕���u���@�zݎ���*�|&q�]�:���~õ�ƮR�\�5Z���>�L����d��:�F�"K���i0�����"o��������z����W����kK},�e��c��v�e��,��=T����>�����i>Ur����Ѷ�$��'rŪ�꾏7��w[j��K�\�"��6�E.-;�d�4���{���"�{m�l�4Hz���l���!����Ϧ��vY�p���2��YYit����1�w���a�"8���
E��CUmC��m���"!
DV�lk; ��i�j�J�����%LD̖j�o�O�0z{���b�	@��ӷJ�U��6S���ܦy�H�8�Q*յ���<LZ�$�PpD�U����2}�<w�̼���ܧ/O��U����<���R�=��:�A�^ʉ�c��ݎ����x�ViB5Y��m*�ҿo�#����Ǚ�)<i}��J_{*d��?��_�qi�W�8�����i6�W�,%���kK��-���ͷ��3~kV�S�p��v��?��ȏ-x6��z�3�'�� ���+[xj��>���B�/��
leܼʁf�i>���]��;+����ޟQ�4з���FE#���,��'��Ϳ�b}�f��P�����ܛ�3�=+������ЕЋ�A�?����Y����,K�(t�G��>�d��\2�'��㴯=2�	�4O�*M�_:�6��T����
ys�z{`�L�z)'F�Il�q��?<�L�+�&˨��ܧ/O��Up�G`.D�T�f~瓁V/I� ��b����r|�Vi���ES���ܦy�pog�f� �%"fK��7���s��#P��	�_�+u�o��0�k�J��gg�@�T("��j�?6�m���"!�;��Yu��pT��J����NC��b4L���6���o� Q<�+>6�]�����c1����ǆUt1��Sd�vo���L�����4H�C9# "�ǃ����������R��<����l�����P}jvz�d>�1�{��S������k}6�zA��+?��מ>��5ӑ�{m���h�i8̾6x�JIr��hu��h2��w���>x���,��b�A�;fA�Ł%���e"�/Vj����b0�u;ڋZ���e4$����]wy���	�l^�(�z�E�:��Ҩה���&���^׺��k�=�@0|�Jf��bT�:Dt�❔I^�����߳m�y�j[�r�A�=�	b*+��d�!�wy)���9: 	�֊�:�y��0�1����B��$���G�0p>���{1�ʖ��a��X������9�9��sn�رYη3���S�Q�aX��`Y�	є��n����v{�O���#����>����D�7��M������ ��ӱ��l3v���l8�'��;܆gD��ŊT8���Hg�|�L++��
�Œo    [remap]

importer="texture"
type="CompressedTexture2D"
uid="uid://xwk6cq2cfewn"
path="res://.godot/imported/AdminOrb.png-572405b0741027976de96989c70a77b6.ctex"
metadata={
"vram_texture": false
}
             RSRC                    PackedScene            ��������                                                  resource_local_to_scene    resource_name 	   _bundled    script    
   Texture2D (   res://Ships/Debug/AdminOrb/AdminOrb.png 1&�BZ      local://PackedScene_pfywy "         PackedScene          	         names "      	   AdminOrb    Node2D 	   Sprite2D    texture    	   variants                       node_count             nodes        ��������       ����                      ����                    conn_count              conns               node_paths              editable_instances              version             RSRC              extends Node2D

var projectile = preload("res://Entities/BaseProjectile/BaseProjectile.tscn")

# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
   RSRC                    PackedScene            ��������                                                  resource_local_to_scene    resource_name 	   _bundled    script       Script 9   res://Ships/Modules/BaseModules/BaseWeapon/BaseWeapon.gd ��������      local://PackedScene_drtrw 0         PackedScene          	         names "         BaseWeapon    script    Node2D 	   Sprite2D    	   variants                       node_count             nodes        ��������       ����                            ����              conn_count              conns               node_paths              editable_instances              version             RSRC               GST2              ����                          �   RIFF�   WEBPVP8L�   /�?@&`�^.M�$�Z �L֝F�yj`\_2��� �
04'�kE8�m7n�xh��t�F ���J[�g�m#v�n=���=��1$����H�Ԝ�&����)a���n��������i25K�����Ok��b��q�i��jCY��� $��0��w5tG�4Q=`��5���"�xT�I�/�9d�f������   [remap]

importer="texture"
type="CompressedTexture2D"
uid="uid://bbch4jfjfn3bg"
path="res://.godot/imported/miningDrill.png-1d4d599d25e2fa7d146dab4cbaacfee2.ctex"
metadata={
"vram_texture": false
}
         RSRC                    PackedScene            ��������                                                  resource_local_to_scene    resource_name 	   _bundled    script    
   Texture2D F   res://Ships/Modules/ExternalModules/Tools/MiningDrill/miningDrill.png ���|��'"      local://PackedScene_rcsd8 @         PackedScene          	         names "         Node2D 	   rotation 	   Sprite2D    texture    	   variants          �I@                node_count             nodes        ��������        ����                            ����                          conn_count              conns               node_paths              editable_instances              version             RSRC        extends "res://Ships/BaseShip/BaseShip.gd"


# Called when the node enters the scene tree for the first time.



# Called every frame. 'delta' is the elapsed time since the previous frame.

  RSRC                    PackedScene            ��������                                                  resource_local_to_scene    resource_name    custom_solver_bias    size    script 	   _bundled       Script /   res://Ships/SovietRocketOne/SovietRocketOne.gd ��������
   Texture2D 8   res://Ships/SovietRocketOne/SovietRocketOneAirframe.png ��?�P�E      local://RectangleShape2D_bukiv �         local://PackedScene_i2q2g �         RectangleShape2D       
     gC  B         PackedScene          	         names "   
      SovietRocketOne    script    CharacterBody2D    CollisionShape2D    shape 	   Sprite2D 	   position    texture    externalModuleContainer    Node2D    	   variants                           
         �?         
     B  �      node_count             nodes     &   ��������       ����                            ����                           ����                           	      ����                   conn_count              conns               node_paths              editable_instances              version             RSRC      GST2   �   P      ����               � P        �  RIFF�  WEBPVP8L�  /��o�&���(��0
���:���FR���>���Z5��@�}��~�"�G���?�+�)��@cpDR5�#m{�6�U���~�,'`G�*��
ΦZ���I%����!Uټ���
�~l��.ED�!8���#EC驊[����B��8X�B8bx��K���Z[1�ʡX� E�b+�t*���P:�kt$��ª��<� 	D��;�Fۧ�"N������č��F�?����ܫ@ݛ[��Q�n�.�W�~��q ��
��B�Vzz�
i�ڀVu�@u����2)5BD���)�g6�J6�y<�f��@ӻi,��X��:�~�D�Ź�ܱ��.��b�yP�B.Cr��풓 ��C���CL�mB�CE��yL�����o�
\kЇ�r�]��SA�'Uid�4?�L�[1��S�ϔ{$$���`���(���gw\Ӯ4 j�6��X�)"���sf���
kpV���� u����ݦ�v0�V�u0`�T�5�������0P;M��x0\W ������FX&�����q�7(��Fq��.��%X �M�����@�f��� �Z���aN�.\���4�s/p-_ 5.���H�ԗO��>��l�d�Z��q��Ͻ�����-�+��Y�!پa�����7���^�t0׫���&4p��I/��Os�W'	�A<�����s0鹏hJS����� Th_��*=���.�m���AQr:�.�:��0;�mr6�J>��!����]�8:~���_�EI�~�����Xlh��<����ˉ=�i��B�h��FB˵��5m��b}Ņ�) s0m��d.W 8�>)H�� $��n�� $�)q�r������K�,m�=I�.p���$���LST9ʆ#TTd�\�+V�#U\945]���`e[remap]

importer="texture"
type="CompressedTexture2D"
uid="uid://cd1n6xxur6dil"
path="res://.godot/imported/SovietRocketOneAirframe.png-e75d5c33df5cbee3f6b0ebc39f376efc.ctex"
metadata={
"vram_texture": false
}
             class_name ExternalModule extends Node2D

@export var relX: float
@export var relY: float

# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
       RSRC                    PackedScene            ��������                                                  resource_local_to_scene    resource_name 	   _bundled    script       Script    res://Ships/ExternalModule.gd ��������      local://PackedScene_x6kss          PackedScene          	         names "         Node2D    script 	   Sprite2D    	   variants                       node_count             nodes        ��������        ����                            ����              conn_count              conns               node_paths              editable_instances              version             RSRC         class_name InternalModule extends Node2D

@export var relX: float
@export var relY: float



# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
     RSRC                    PackedScene            ��������                                                  resource_local_to_scene    resource_name 	   _bundled    script       Script    res://Ships/InternalModule.gd ��������      local://PackedScene_pdhkw          PackedScene          	         names "         Node2D    script    	   variants                       node_count             nodes     	   ��������        ����                    conn_count              conns               node_paths              editable_instances              version             RSRC  extends Camera2D

var speed = 15
var zoomSpeed = Vector2(.2,.2)
# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	if(Input.is_action_pressed("CameraUp")):
		position.y -= speed
	if(Input.is_action_pressed("CameraDown")):
		position.y += speed
	if(Input.is_action_pressed("CameraLeft")):
		position.x -= speed
	if(Input.is_action_pressed("CameraRight")):
		position.x += speed
	
func _input(event):
	if(event.is_action_pressed("CameraZoomIn")):
		zoom += zoomSpeed
	if(event.is_action_pressed("CameraZoomOut")):
		zoom -= zoomSpeed
      extends Node2D

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
	var currentSlice = message.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	entityId = currentSlice.decode_s32(0)
	currentIndex += 4
	
	currentSlice = message.slice(currentIndex, currentIndex + 1)
	currentSlice.reverse()
	entityType = currentSlice.decode_s8(0)
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
		var json = JSON.new()
		var error = json.parse(jsonString)
		if error == OK:
			var data = json.data
			print(data.test)
			print(data.externalModules[0].moduleName)
			
			newEntity = sovietRocketOne.instantiate()
			
			for i in range(data.externalModules.size()):
				var moduleScene = load(data.externalModules[i].scenePath)

				if(moduleScene != null):
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
	var currentSlice = message.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	entityId = currentSlice.decode_s32(0)
	currentIndex += 4
	
	currentSlice = message.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	positionX = currentSlice.decode_float(0)
	currentIndex += 4
	print(positionX)
	
	currentSlice = message.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	positionY = currentSlice.decode_float(0)
	currentIndex += 4
	
	#java encodes in big endian, need to reverse the floats
	var velocitySlice = message.slice(currentIndex, currentIndex + 4)
	velocitySlice.reverse()
	velocityX = velocitySlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = message.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	velocityY = currentSlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = message.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	health = currentSlice.decode_s32(0)
	currentIndex += 4
	
	currentSlice = message.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	rotation = currentSlice.decode_float(0)
	currentIndex += 4
	
	currentSlice = message.slice(currentIndex, currentIndex + 4)
	currentSlice.reverse()
	rotationalVelocity = currentSlice.decode_float(0)
	
	
	#modify entity
	var entity = entityDictionary.get(entityId)
	if(entity != null):
		entity.position = Vector2(positionX, positionY)
		entity.velocity = Vector2(velocityX, velocityY)
		entity.angularVelocity = rotationalVelocity
		#entity.spriteRotation = rotation
		entity.rotation = rotation
		print("velocityx: " + str(velocityX))
		
			

		#print("y: " + str(positionY))
		#print("rotation: " + str(rotation))


       GST2   �   �      ����               � �        �  RIFF�  WEBPVP8L�  /������!"2�H�m�m۬�}�p,��5xi�d�M���)3��$�V������3���$G�$2#�Z��v{Z�lێ=W�~� �����d�vF���h���ڋ��F����1��ڶ�i�엵���bVff3/���Vff���Ҿ%���qd���m�J�}����t�"<�,���`B �m���]ILb�����Cp�F�D�=���c*��XA6���$
2#�E.@$���A.T�p )��#L��;Ev9	Б )��D)�f(qA�r�3A�,#ѐA6��npy:<ƨ�Ӱ����dK���|��m�v�N�>��n�e�(�	>����ٍ!x��y�:��9��4�C���#�Ka���9�i]9m��h�{Bb�k@�t��:s����¼@>&�r� ��w�GA����ը>�l�;��:�
�wT���]�i]zݥ~@o��>l�|�2�Ż}�:�S�;5�-�¸ߥW�vi�OA�x��Wwk�f��{�+�h�i�
4�˰^91��z�8�(��yޔ7֛�;0����^en2�2i�s�)3�E�f��Lt�YZ���f-�[u2}��^q����P��r��v��
�Dd��ݷ@��&���F2�%�XZ!�5�.s�:�!�Њ�Ǝ��(��e!m��E$IQ�=VX'�E1oܪì�v��47�Fы�K챂D�Z�#[1-�7�Js��!�W.3׹p���R�R�Ctb������y��lT ��Z�4�729f�Ј)w��T0Ĕ�ix�\�b�9�<%�#Ɩs�Z�O�mjX �qZ0W����E�Y�ڨD!�$G�v����BJ�f|pq8��5�g�o��9�l�?���Q˝+U�	>�7�K��z�t����n�H�+��FbQ9���3g-UCv���-�n�*���E��A�҂
�Dʶ� ��WA�d�j��+�5�Ȓ���"���n�U��^�����$G��WX+\^�"�h.���M�3�e.
����MX�K,�Jfѕ*N�^�o2��:ՙ�#o�e.
��p�"<W22ENd�4B�V4x0=حZ�y����\^�J��dg��_4�oW�d�ĭ:Q��7c�ڡ��
A>��E�q�e-��2�=Ϲkh���*���jh�?4�QK��y@'�����zu;<-��|�����Y٠m|�+ۡII+^���L5j+�QK]����I �y��[�����(}�*>+���$��A3�EPg�K{��_;�v�K@���U��� gO��g��F� ���gW� �#J$��U~��-��u���������N�@���2@1��Vs���Ŷ`����Dd$R�":$ x��@�t���+D�}� \F�|��h��>�B�����B#�*6��  ��:���< ���=�P!���G@0��a��N�D�'hX�׀ "5#�l"j߸��n������w@ K�@A3�c s`\���J2�@#�_ 8�����I1�&��EN � 3T�����MEp9N�@�B���?ϓb�C��� � ��+�����N-s�M�  ��k���yA 7 �%@��&��c��� �4�{� � �����"(�ԗ�� �t�!"��TJN�2�O~� fB�R3?�������`��@�f!zD��%|��Z��ʈX��Ǐ�^�b��#5� }ى`�u�S6�F�"'U�JB/!5�>ԫ�������/��;	��O�!z����@�/�'�F�D"#��h�a �׆\-������ Xf  @ �q�`��鎊��M��T�� ���0���}�x^�����.�s�l�>�.�O��J�d/F�ě|+^�3�BS����>2S����L�2ޣm�=�Έ���[��6>���TъÞ.<m�3^iжC���D5�抺�����wO"F�Qv�ږ�Po͕ʾ��"��B��כS�p�
��E1e�������*c�������v���%'ž��&=�Y�ް>1�/E������}�_��#��|������ФT7׉����u������>����0����緗?47�j�b^�7�ě�5�7�����|t�H�Ե�1#�~��>�̮�|/y�,ol�|o.��QJ rmϘO���:��n�ϯ�1�Z��ը�u9�A������Yg��a�\���x���l���(����L��a��q��%`�O6~1�9���d�O{�Vd��	��r\�՜Yd$�,�P'�~�|Z!�v{�N�`���T����3?DwD��X3l �����*����7l�h����	;�ߚ�;h���i�0�6	>��-�/�&}% %��8���=+��N�1�Ye��宠p�kb_����$P�i�5�]��:��Wb�����������ě|��[3l����`��# -���KQ�W�O��eǛ�"�7�Ƭ�љ�WZ�:|���є9�Y5�m7�����o������F^ߋ������������������Р��Ze�>�������������?H^����&=����~�?ڭ�>���Np�3��~���J�5jk�5!ˀ�"�aM��Z%�-,�QU⃳����m����:�#��������<�o�����ۇ���ˇ/�u�S9��������ٲG}��?~<�]��?>��u��9��_7=}�����~����jN���2�%>�K�C�T���"������Ģ~$�Cc�J�I�s�? wڻU���ə��KJ7����+U%��$x�6
�$0�T����E45������G���U7�3��Z��󴘶�L�������^	dW{q����d�lQ-��u.�:{�������Q��_'�X*�e�:�7��.1�#���(� �k����E�Q��=�	�:e[����u��	�*�PF%*"+B��QKc˪�:Y��ـĘ��ʴ�b�1�������\w����n���l镲��l��i#����!WĶ��L}rեm|�{�\�<mۇ�B�HQ���m�����x�a�j9.�cRD�@��fi9O�.e�@�+�4�<�������v4�[���#bD�j��W����֢4�[>.�c�1-�R�����N�v��[�O�>��v�e�66$����P
�HQ��9���r�	5FO� �<���1f����kH���e�;����ˆB�1C���j@��qdK|
����4ŧ�f�Q��+�     [remap]

importer="texture"
type="CompressedTexture2D"
uid="uid://cvt1pp10qx6vp"
path="res://.godot/imported/icon.svg-218a8f2b3041327d8a5756f3a245f83b.ctex"
metadata={
"vram_texture": false
}
                extends Node2D

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
var angle: float = 0
var previousAngle: float = 0

# Called when the node enters the scene tree for the first time.
func _ready():
	webSocketHandler = get_node("../WebSocketHandler")



#FIX NEEDED: add known acceleration to local velocity to reduce rubber banding in movement while accelerating
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
	#Place desired angle from mouse
	var mousePosition = get_global_mouse_position()
	var direction = (mousePosition - global_position).normalized()
	angle = direction.angle()
	if(angle != previousAngle):
		inputChanged = true
	previousAngle = angle
	
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
		#print("SIZE: " + str(message.size()))
		#print("Byte: " + str(inputByte))
		print("ANGLE: " + str(angle))
		var floatArray = PackedByteArray()
		floatArray.resize(4)
		floatArray.encode_float(0, angle)
		floatArray.reverse()
		message.append_array(floatArray)
		
		webSocketHandler.sendMessage(message)
           RSRC                    PackedScene            ��������                                                  ..    WebSocketHandler    resource_local_to_scene    resource_name 	   _bundled    script       Script    res://WebSocketHandler.gd ��������   Script    res://EntityHandler.gd ��������   Script    res://Camera2D.gd ��������   Script    res://InputHandler.gd ��������      local://PackedScene_ejrdc �         PackedScene          	         names "      
   MainScene    Node2D    WebSocketHandler    script    EntityHandler    webSocketHandler 	   Camera2D    InputHandler    	   variants                                                               node_count             nodes     -   ��������       ����                      ����                            ����           @                     ����                           ����                   conn_count              conns               node_paths              editable_instances              version             RSRC          GST2   <         ����               <         &   RIFF   WEBPVP8L   /;� Њ2W�����    [remap]

importer="texture"
type="CompressedTexture2D"
uid="uid://dv4so60ot7q74"
path="res://.godot/imported/purple.png-993a3fae7325db95475822da4ec84ea6.ctex"
metadata={
"vram_texture": false
}
              extends Node

var socket = WebSocketPeer.new()

var sent = false

signal entity_update_message(message)
signal new_entity_message(message)
signal entity_delete_message(message)
signal sector_join_message(message)
signal error_message(message)

var entityHandler
var inputHandler
var debugMode = false
var requestComplete = false

# Called when the node enters the scene tree for the first time.
func _ready():
	print("connecting")
	var error
	if debugMode == true:
		error = socket.connect_to_url("ws://localhost:8080/openGameSession/Cnidarian/BDdvbh3M_BZqy7_z07GxvA==")
		requestComplete = true
	else:
		var token = JavaScriptBridge.eval("sessionStorage.getItem('accessToken')")
		var url = JavaScriptBridge.eval("sessionStorage.getItem('url')")
		#var url = "https://winapimonitoring.com"
		var headers = ["Content-Type: application/json", "Authorization: Bearer " + str(token)]
		var httpRequest = HTTPRequest.new()
		add_child(httpRequest)
		var body = JSON.new().stringify({"message": "test"})
		httpRequest.request_completed.connect(self.getTokenRequestComplete)
		httpRequest.request(url + "/api/getGameToken", headers, HTTPClient.METHOD_POST, body)
		
	#var error = socket.connect_to_url("ws://localhost:8080/openGameSession/Cnidarian2/uXVlqbA27aCYXtrIdxpQJw==");
	print(error);
	entityHandler = get_node("../EntityHandler")
	inputHandler = get_node("../InputHandler")
	
func getTokenRequestComplete(result, response_code, headers, body):
	print(body)
	var username = JavaScriptBridge.eval("sessionStorage.getItem('username')")
	var httpUrl = JavaScriptBridge.eval("sessionStorage.getItem('url')")
	
	#var url1 = String('wss://winapimonitoring.com/openGameSession/')
	var url1 = httpUrl + "/openGameSession/"
	if(url1.find("localhost", 0) == -1):
		url1 = url1.replace("https", "wss")
	else:
		url1 = String("ws://localhost:8080/openGameSession/")
	print("line 44: " + str(url1))
	url1 = url1 + username + "/"
	var json = JSON.new()
	json.parse(body.get_string_from_utf8())
	var response = json.get_data()

	url1 = url1 + response.gameSessionToken
	
	print(response.gameSessionToken)
	print("Line 53: " + url1)
	
	socket.connect_to_url(url1)
	requestComplete = true
	pass
# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	if requestComplete == true:
		socket.poll()
		var state = socket.get_ready_state()
		if state == WebSocketPeer.STATE_OPEN:
			if(sent == false):
				var value = PackedByteArray()
				value.append(3)
				socket.send(value);
				sent = true
			while socket.get_available_packet_count():
				var packet = socket.get_packet()
				print("packet: ", packet)
				#Get message type
				var type = packet.decode_s8(0)
				match type:
					#player info
					0:
						print("player info")
					#sector join
					1:
						print("sector join")
					#error
					2:
						print("error")
					#join debug
					3:
						print("join debug")
					
					#entity update
					4:
						entityHandler.handleEntityUpdate(packet)
						print("entity update")
					
					#new entity notification
					5: 
						entityHandler.handleNewEntity(packet)
						print("new entity notification")
						
		elif state == WebSocketPeer.STATE_CLOSING:
			
			pass
		elif state == WebSocketPeer.STATE_CLOSED:
			var code = socket.get_close_code()
			var reason = socket.get_close_reason()
			print("Web socket closed with code %d reason %s", code ,reason);
			#set_process(false)

func sendMessage(message: PackedByteArray):
	socket.send(message)
	pass
            [remap]

path="res://.godot/exported/133200997/export-c7b3668845cd4ce49ae464e4e1f8f516-BaseProjectile.scn"
     [remap]

path="res://.godot/exported/133200997/export-5c5b3d93a3b232a2de49b17fcd982c47-BaseShip.scn"
           [remap]

path="res://.godot/exported/133200997/export-f917fe205a6922e255b6f4deea94106e-AdminOrb.scn"
           [remap]

path="res://.godot/exported/133200997/export-c4d36427f61dc3c33acb0756fa918854-BaseWeapon.scn"
         [remap]

path="res://.godot/exported/133200997/export-945b6b44938192d43dbc1021d519b07c-MiningDrill.scn"
        [remap]

path="res://.godot/exported/133200997/export-81ebc570ea2082422c22a7ebc780f341-SovietRocketOne.scn"
    [remap]

path="res://.godot/exported/133200997/export-d5f8655d686f4268a103cd62bf5f3dac-ExternalModule.scn"
     [remap]

path="res://.godot/exported/133200997/export-b6d946d4138cc1c6d0c9dfbaeef38f77-InternalModule.scn"
     [remap]

path="res://.godot/exported/133200997/export-a00ca0e08a1ac5e46d330a7c1b8d7acb-MainScene.scn"
          list=Array[Dictionary]([{
"base": &"Node2D",
"class": &"ExternalModule",
"icon": "",
"language": &"GDScript",
"path": "res://Ships/ExternalModule.gd"
}, {
"base": &"Node2D",
"class": &"InternalModule",
"icon": "",
"language": &"GDScript",
"path": "res://Ships/InternalModule.gd"
}])
     <svg height="128" width="128" xmlns="http://www.w3.org/2000/svg"><rect x="2" y="2" width="124" height="124" rx="14" fill="#363d52" stroke="#212532" stroke-width="4"/><g transform="scale(.101) translate(122 122)"><g fill="#fff"><path d="M105 673v33q407 354 814 0v-33z"/><path d="m105 673 152 14q12 1 15 14l4 67 132 10 8-61q2-11 15-15h162q13 4 15 15l8 61 132-10 4-67q3-13 15-14l152-14V427q30-39 56-81-35-59-83-108-43 20-82 47-40-37-88-64 7-51 8-102-59-28-123-42-26 43-46 89-49-7-98 0-20-46-46-89-64 14-123 42 1 51 8 102-48 27-88 64-39-27-82-47-48 49-83 108 26 42 56 81zm0 33v39c0 276 813 276 814 0v-39l-134 12-5 69q-2 10-14 13l-162 11q-12 0-16-11l-10-65H446l-10 65q-4 11-16 11l-162-11q-12-3-14-13l-5-69z" fill="#478cbf"/><path d="M483 600c0 34 58 34 58 0v-86c0-34-58-34-58 0z"/><circle cx="725" cy="526" r="90"/><circle cx="299" cy="526" r="90"/></g><g fill="#414042"><circle cx="307" cy="532" r="60"/><circle cx="717" cy="532" r="60"/></g></g></svg>
              ����]I�i1   res://Entities/BaseProjectile/BaseProjectile.tscn5cּ�Pu"   res://Ships/BaseShip/BaseShip.tscn1&�BZ'   res://Ships/Debug/AdminOrb/AdminOrb.png�G�[WH(   res://Ships/Debug/AdminOrb/AdminOrb.tscn����
:   res://Ships/Modules/BaseModules/BaseWeapon/BaseWeapon.tscn���|��'"E   res://Ships/Modules/ExternalModules/Tools/MiningDrill/miningDrill.png׌O���>F   res://Ships/Modules/ExternalModules/Tools/MiningDrill/MiningDrill.tscn���/-0   res://Ships/SovietRocketOne/SovietRocketOne.tscn��?�P�E7   res://Ships/SovietRocketOne/SovietRocketOneAirframe.png�j+ڞ�   res://Ships/ExternalModule.tscn��Ţ}�   res://Ships/InternalModule.tscn-��,BW   res://icon.svg`���b�+   res://MainScene.tscnM,���x   res://purple.png      ECFG      application/config/name         Godot-client   application/run/main_scene         res://MainScene.tscn   application/config/features(   "         4.2    GL Compatibility       application/config/icon         res://icon.svg     display/window/stretch/mode         viewport   input/CameraUp�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device         	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode     @    physical_keycode    @ 	   key_label       @    unicode           echo          script         input/CameraDown�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode    @ 	   key_label             unicode           echo          script         input/CameraLeft�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode    @ 	   key_label             unicode           echo          script         input/CameraRight�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode    @ 	   key_label             unicode           echo          script         input/CameraZoomIn�              deadzone      ?      events              InputEventMouseButton         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          button_mask           position              global_position               factor       �?   button_index         canceled          pressed           double_click          script         input/CameraZoomOut�              deadzone      ?      events              InputEventMouseButton         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          button_mask           position              global_position               factor       �?   button_index         canceled          pressed           double_click          script         input/w_down�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode   W   	   key_label             unicode    w      echo          script         input/a_down�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode   A   	   key_label             unicode    a      echo          script         input/s_down�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode   S   	   key_label             unicode    s      echo          script         input/d_down�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode   D   	   key_label             unicode    d      echo          script         input/space_down�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode       	   key_label             unicode           echo          script         input/q_down�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode   Q   	   key_label             unicode    q      echo          script         input/e_down�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode   E   	   key_label             unicode    e      echo          script         input/f_down�              deadzone      ?      events              InputEventKey         resource_local_to_scene           resource_name             device     ����	   window_id             alt_pressed           shift_pressed             ctrl_pressed          meta_pressed          pressed           keycode           physical_keycode   F   	   key_label             unicode    f      echo          script      9   rendering/textures/canvas_textures/default_texture_filter          #   rendering/renderer/rendering_method         gl_compatibility*   rendering/renderer/rendering_method.mobile         gl_compatibility    