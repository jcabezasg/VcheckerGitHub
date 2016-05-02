package Model;

public class Art {
	String artifact;
	String version;
	String server;
	
	public Art(){}
	
	public Art(String artifact, String version, String server) {
		this.artifact = artifact;
		this.version = version;
		this.server = server;
	}

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}
}

