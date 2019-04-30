function AjaxRequest(opts) {
	this.type = opts.type || "get"
	this.url = opts.url
	this.param = opts.param || {}
	this.isShowLoader = opts.isShowLoader || false
	this.dataType = opts.dataType || "json"
	this.callBack = opts.callBack
	this.init()
}
AjaxRequest.prototype = {
	//初始化
	init: function() {
		this.sendRequest()
	},
	//渲染loader
	showLoader: function() {
		if (this.isShowLoader) {
			var loader = '<div class="ajaxLoader"><div class="loader">加载中...</div></div>'
			$("body").append(loader)
		}
	},
	//隐藏loader
	hideLoader: function() {
		if (this.isShowLoader) {
			$(".ajaxLoader").remove()
		}
	},
	//发送请求
	sendRequest: function() {
		var self = this
		$.ajax({
			type: this.type,
			url: this.url,
			data: this.param,
			dataType: this.dataType,
			beforeSend: this.showLoader(),
			success: function(res) {
				self.hideLoader()
				if (res != null && res != "") {
					if (self.callBack) {
						if (Object.prototype.toString.call(self.callBack) === "[object Function]") { //Object.prototype.toString.call方法--精确判断对象的类型
							self.callBack(res)
						} else {
							console.log("callBack is not a function")
						}
					}
				}
			}
		})
	}
}
