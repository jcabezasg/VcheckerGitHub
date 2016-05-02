package Model;

	import java.util.List;

	public class ArtifactList {
		
		List<Art> artifacts;
		
		ArtifactList(){}

		public ArtifactList(List<Art> artifacts) {
			this.artifacts = artifacts;
		}

		public List<Art> getArtifacts() {
			return artifacts;
		}

		public void setArtifacts(List<Art> artifacts) {
			this.artifacts = artifacts;
		}	
	}

