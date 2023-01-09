<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>

				<petclinic:menuItem active="${name eq 'games'}" url="/games"
					title="Games">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Games</span>
				</petclinic:menuItem>
				<petclinic:menuItem active="${name eq 'achievements'}" url="/achievements"
					title="Achievements" dropdown="${true}">
					<ul class="dropdown-menu">
						<li>
							<a href="<c:url value="/achievements"/> ">Achievements listing</a>
						</li>
						<li class="divider"/>
						<li>
							<a href="<c:url value="/myAchievements" />">My achievements <span class="glyphicon glyphicon-certificate" aria-hidden="true"></span></a>
						</li>
					</ul>
				</petclinic:menuItem>
				<petclinic:menuItem active="${name eq 'statistics'}" url="/stats"
					title="Statistics" dropdown="${true}">
					<ul class="dropdown-menu">
						<li>
							<a href="<c:url value="/stats"/> ">My statistics</a>
						</li>
						<li class="divider"/>
						<li>
							<a href="<c:url value="/global" />">Global stadistics<span class="glyphicon glyphicon-certificate" aria-hidden="true"></span></a>
						</li>
					</ul>
				</petclinic:menuItem>
				<petclinic:menuItem active="${name eq 'friends'}" url="/users/friends"
					title="Friends">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Friends</span>
				</petclinic:menuItem>
				<petclinic:menuItem active="${name eq 'notifications'}" url="/notifications/redirect"
					title="Notifications">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Notifications</span>
				</petclinic:menuItem>
				<sec:authorize access="hasAuthority('admin')">
					<petclinic:menuItem active="${name eq 'Admin'}" url="/admin"
						title="Admin">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Admin</span>
					</petclinic:menuItem>
				</sec:authorize>
			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span> 
								<strong><sec:authentication property="name" /></strong> <span
								class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
											<p class="text-left">
												<a href="<c:url value="/profile" />"
													class="btn btn-primary btn-block btn-sm">Profile</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
