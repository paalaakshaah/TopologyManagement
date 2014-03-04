JFLAGS = -g
JC = javac#~/../../usr/lib/jvm/java-6-openjdk/bin/javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
	Address.java\
        Node.java \
        Topology.java \
        TMAN.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class
