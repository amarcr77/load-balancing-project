<!DOCTYPE html>
<html>
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
<head>
<title>LoadBalancing Home</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/animate.css">
<link rel="stylesheet" type="text/css" href="assets/css/slick.css">
<link rel="stylesheet" type="text/css" href="assets/css/theme.css">
<link rel="stylesheet" type="text/css" href="assets/css/style.css">
<!--[if lt IE 9]>
<script src="assets/js/html5shiv.min.js"></script>
<script src="assets/js/respond.min.js"></script>
<![endif]-->
</head>
<body>
<div id="preloader">
  <div id="status">&nbsp;</div>
</div>
<a class="scrollToTop" href="#"><i class="fa fa-angle-up"></i></a>
<div class="container">
  <header id="header">
    <div class="row">
      <div class="col-lg-12 col-md-12">
        <div class="header_bottom">
          <div class="header_bottom_left"><a class="logo" href="index.html">Load<strong>Balancing</strong>&nbsp;&nbsp;Cloud2</a></div>
        </div>
      </div>
    </div>
  </header>
  <div id="navarea">
    <nav class="navbar navbar-default" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav custom_nav">
            <li class=""><a href="#">Home</a></li>
            <li><a href="#">About Us</a></li>
            <li><a href="#">Contact</a></li>
          </ul>
		  <div id="welcome_greet">
			Welcome, <%= username%>
		  </div>
        </div>
      </div>
    </nav>
  </div>
  <section id="mainContent">
    <div class="content_top">
      <div class="row">
        <div class="col-lg-6 col-md-6 col-sm6">
          <div class="latest_slider">
            <div class="slick_slider">
              <div class="single_iteam"><img src="images/sports2.jpg" alt="">
                <h2><a class="slider_tittle" href="pages/#">Sports For Life</a></h2>
              </div>
              <div class="single_iteam"><img src="images/news.jpg" alt="">
                <h2><a class="slider_tittle" href="pages/#">Stay Updated with latest News</a></h2>
              </div>
              <div class="single_iteam"><img src="images/maxresdefault.jpg" alt="">
                <h2><a class="slider_tittle" href="pages/bollywood_home.jsp">Watch Hot songs of bollywood and hollywood!</a></h2>
              </div>
              <div class="single_iteam"><img src="images/discovery.jpg" alt="">
                <h2><a class="slider_tittle" href="pages/#">Get amazed with wonderfull nature</a></h2>
              </div>
            </div>
          </div>
        </div>
        <div class="col-lg-6 col-md-6 col-sm6">
          <div class="content_top_right">
            <ul class="featured_nav wow fadeInDown">
              <li><img src="images/download.jpg" alt="">
                <div class="title_caption"><a href="sports_home.jsp">Sports</a></div>
              </li>
              <li><img src="images/manvswild.jpg" alt="">
                <div class="title_caption"><a href="discovery_home.jsp">Discovery</a></div>
              </li>
              <li><img src="images/bestofbollywood.jpg" alt="">
                <div class="title_caption"><a href="bollywood_home.jsp">Movies and Songs</a></div>
              </li>
              <li><img src="images/obama.jpg" alt="">
                <div class="title_caption"><a href="news_home.jsp">News</a></div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
   
   
    </section>
	<div id="logout_btn"><a href="http://loadbalancingproject.appspot.com/logout?cloud=2" id="logout_href">logout</a></div>
</div>

<script src="assets/js/jquery.min.js"></script> 
<script src="assets/js/bootstrap.min.js"></script> 
<script src="assets/js/wow.min.js"></script> 
<script src="assets/js/slick.min.js"></script> 
<script src="assets/js/custom.js"></script>
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