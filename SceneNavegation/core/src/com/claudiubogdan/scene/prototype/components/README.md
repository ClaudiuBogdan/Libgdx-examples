Components are a great way to assign properties to entities. They allow you to specify the
attributes that apply to specific entities. An enemy might have a position component, a
model component, a velocity component, and a render component, while an obstacle might
have just the position component, the model component, and the render component. After
this brief introduction, we will get started with a simple component.

# Bullet Physics and Bullet system

Bullet Physics is an ideal way of handling all the collisions and has a lot of extra
functionality we could make use of, such as gravity. It's a library written in C++. Although
all the basic functionality can be accessed via the Java Native Interface (JNI), the library
also contains some extra helping functions, which we will use.