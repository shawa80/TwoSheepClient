package com.siliconandsynapse.ixcpp.gameInteraction;


public class GameInfo {

	

		private int gameId;
		private String name;
		
		public GameInfo(int gameId, String name)
		{
			this.gameId = gameId;
			this.name = name;
		}
		
		public String getName() {
			
			return name;
		}
		
		public int getId() {
			return gameId;
			
		}
		
		@Override
		public String toString() {
			
			return gameId + " " + name;
		}
		

		@Override
		public boolean equals(Object o) {

			if (o instanceof GameInfo) {
				
				GameInfo d = (GameInfo)o;
				
				if (d.gameId == gameId)
					return true;
			}
			

			return false;
		}

		@Override
		public int hashCode() {

			
			return ("" + gameId).hashCode();
			
		}
		
		

	
}
