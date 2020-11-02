[issues]: https://github.com/PrimeAPI/animated-guiapi/issues
# GuiAPI - Easy way to create animated spigot GUI's

How to include the API with Maven: 
```xml
<repositories>
	<repository>
		<id>repo.primeapi.de</id>
   		<url>https://repo.primeapi.de/</url>
	</repository>
</repositories>

<dependencies>
    	<dependency>
        	<groupId>de.primeapi</groupId>
        	<artifactId>guiapi</artifactId>
        	<version>1.0.0</version>
        	<scope>compile</scope>
    	</dependency>
</dependencies>
```

## How to use it

#### Setting up the API
Simply add ```new GUIManager(Plugin)``` in your onEnable():
```java
    public GUIManager guiManager;
  
    @Override
    public void onEnable() {
      guiManager = new GUIManager(this);
    }
```

#### Creating an GUI
To create an GUI use ```new GUIBuilder```
Exmaple:
```java
        GUIBuilder builder = new GUIBuilder(27, "My new GUI");
        builder.addItem(14, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§eClick me!").build(), (player, itemStack) -> {
            player.sendMessage("§aYou clicked the " + itemStack.getType() + "!");
        });
        builder.addCloseAction((player, inventory) -> {
            player.sendMessage("§cYou closed the Inventory!");
        });
        builder.animate(p, GUIManager.createDefaultAnimationConfiguration());
```

#### Opening the GUI
To animate the Inventory use ```GUIBuilder#animate```.
To open the Inventory without an animation use ```Player#openInventory(GUIBuilder#build)```

## Support
- [Issue Tracker][issues]
