//封装$.messager.show（）
function showMsg(showType,showSpeed,title,msg,timeout){
	$.messager.show({
		showType:showType,//show。默认：slide。
		showSpeed:showSpeed,
		title:title,
		msg:msg,
		timeout:timeout,
		style:{
			left:(document.body.scrollWidth)/2-250,
			top:(document.body.scrollHeight)/2-100,
		}
	});
}