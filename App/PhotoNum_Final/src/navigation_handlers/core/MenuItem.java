package navigation_handlers.core;

public class MenuItem {
	private String key;
	private String name;
	private Runnable runnable;


	public MenuItem(String key, String name) {
		this.key = key;
		this.name = name;
	}

	public MenuItem(String key, String name, Runnable runnable) {
		this.key = key;
		this.name = name;
		this.runnable = runnable;
	}


	public String getKey() {
		return key;
	}


	public String getName() {
		return name;
	}


	public Runnable getRunnable() {
		return runnable;
	}
}