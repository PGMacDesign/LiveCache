package pgmacdesign.livecache.samples

class University {
	var name: String? = null
	var address: String? = null

	constructor(name: String?, address: String?){
		this.name = name;
		this.address = address;
	}
	constructor(name: String?){
		this.name = name;
	}
}