$(function(){
	$("#login").submit(function(){
		var name = $.trim($("#username").val());
		var pwd = $.trim($("#password").val());
        sessionStorage.setItem('name', name);
        sessionStorage.setItem('userId', '123456');
		if(name == ""){
			$("#error").html("用户名不能为空");
			return false;
		}
		if(pwd == ""){
			$("#error").html("密码不能为空");
			return false;
		}
		$("#password").val(hex_md5(pwd));
		return true;
	});
});
