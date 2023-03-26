package Entities;

public class Packages {

	String packageNo;
	int weight;
	int width;
	int height;
	int depth;
	String deliveryDate;
	String status;
	int fragile=0;
	String matierial="";
	int chemical=0;
	String riskType="";
	int liquid=0;
	int volume=0;
	String destinationID;
	
	public Packages(String packageNo, int weight, int width, int height, int depth, String deliveryDate, String status,
			String destinationID) {
		super();
		this.packageNo = packageNo;
		this.weight = weight;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.deliveryDate = deliveryDate;
		this.status = status;
		this.destinationID = destinationID;
	}
	

	public Packages(String packageNo, int weight, int width, int height, int depth, String deliveryDate, String status,
			int fragile, String matierial, int chemical, String riskType, int liquid, int volume,
			String destinationID) {
		super();
		this.packageNo = packageNo;
		this.weight = weight;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.deliveryDate = deliveryDate;
		this.status = status;
		this.fragile = fragile;
		this.matierial = matierial;
		this.chemical = chemical;
		this.riskType = riskType;
		this.liquid = liquid;
		this.volume = volume;
		this.destinationID = destinationID;
	}


	public String getPackageNo() {
		return packageNo;
	}

	public int getWeight() {
		return weight;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getDepth() {
		return depth;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public String getStatus() {
		return status;
	}

	public int getFragile() {
		return fragile;
	}

	public String getMatierial() {
		return matierial;
	}

	public int getChemical() {
		return chemical;
	}

	public String getRiskType() {
		return riskType;
	}

	public int getLiquid() {
		return liquid;
	}

	public int getVolume() {
		return volume;
	}

	public String getDestinationID() {
		return destinationID;
	}


	@Override
	public String toString() {
		return "Packages [packageNo=" + packageNo + ", weight=" + weight + ", width=" + width + ", height=" + height
				+ ", depth=" + depth + ", deliveryDate=" + deliveryDate + ", status=" + status + ", fragile=" + fragile
				+ ", matierial=" + matierial + ", chemical=" + chemical + ", riskType=" + riskType + ", liquid="
				+ liquid + ", volume=" + volume + ", destinationID=" + destinationID + "]";
	}
	
	
}
