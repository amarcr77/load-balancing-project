<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<html lang="en">
<% 
if(session!=null)
{
	String accesstoken=session.getAttribute("accesstoken").toString();
	String username=session.getAttribute("username").toString();
	

	if(accesstoken==null || username==null)
	{
		 response.setHeader("Location","http://loadbalancingproject.appspot.com/"); 
	}
	else
	{
%>
<head><title></title></head>
<body>
<h1> Welcome <%= username%> </h1>
<video width="320" height="240" controls>
  <source src="http://r9---sn-q4f7snse.googlevideo.com/videoplayback?sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cnh%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&key=yt6&mime=video%2Fmp4&nh=IgpwcjA1LmRmdzA2Kgs1MC45Ny4xNi4zNg&expire=1473588965&dur=117.701&lmt=1458717468205687&signature=862FA28F41F1BF864CE5AC92231588B105C00EB6.82D48E6BC56308C07A1F0073CC7D6CD19BC078A0&source=youtube&ms=au&mv=m&mt=1473567031&id=o-ADgnlH787s8wIrVHSaBNX3-GXfYAEBT3aq3bD6yJZ1U6&mn=sn-q4f7snse&mm=31&initcwndbps=7346250&upn=H-Y8k3FVccc&ipbits=0&pl=24&ei=hNrUV4aEO4LyWKvznPAM&itag=18&ip=159.253.144.86&ratebypass=yes&sver=3&title=Cristiano+Ronaldo+Wears+First+Self-Lacing+Shoe" type="video/mp4">
  Your browser does not support the video tag.
</video>
</body>
<%
	}//end of else
}//end if
else
{
	 response.setHeader("Location","http://loadbalancingproject.appspot.com/"); 
}
%>
</html>