//封装$.messager.show（）
function showMsg(showType,title,msg,showSpeed,timeout){
	$.messager.show({
		showType:showType,//show。默认：slide。
		showSpeed:showSpeed || 1000,
		title:title,
		msg:msg,
		timeout:timeout || 2000,
		style:{
			left:(document.body.scrollWidth)/2-250,
			top:(document.body.scrollHeight)/2-100,
		}
	});
}