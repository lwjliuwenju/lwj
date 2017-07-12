
 <script type="text/javascript">
	alert(window.location.search);
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
alert(GetQueryString('id'));
</script>
	<a target="aaaa" href="proposer_detailform.action?id=161">aa</a>
	<iframe name="aaaa" width="auto" height="auto"></iframe>
