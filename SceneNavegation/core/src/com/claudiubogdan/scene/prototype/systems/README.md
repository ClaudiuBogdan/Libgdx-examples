Components don't do anything on their own; they need to be put to use somehow. That is
the role of the systems. The systems classes are created by extending the EntitySystem
class from the Ashley library. Each of our systems must have an update() function and an
addedToEngine() function. Everything else is up to you. The update() method gets
called every game tick, and the addedToEngine() is called once the system is added to the
engine. We don't directly call this method anywhere in our code; it gets called by Ashley's
Engine class. 