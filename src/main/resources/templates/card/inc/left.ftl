
<div id="left-panel" class="left_panel">

	<@notices>
		<#list notices_results as row>
			<div class="panel panel-default corner-radius panel-hot-topics">
				<div class="panel-heading" style="padding: 0px 2px;">
					<marquee class="panel-title" style="white-space: nowrap;overflow: hidden;"><i class="fa fa fa-hand-o-right"></i> ${row.title}</marquee>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<a href="${row.url}" target="_blank" style="display: block;">
						<img src="${row.image}" style="width: 100%;height: 100%" />
					</a>
				</div>
			</div>
		</#list>
	</@notices>
</div>

<script>
	$(function () {
		let _onscroll_left = document.onscroll;
		document.onscroll = function() {
			typeof _onscroll_left === "function" ? _onscroll_left() : false;
			var height = $("#left-panel").height();
			var st = $(window).scrollTop();
			var sth = $(window).height();
			if(st + sth > 126 + height) {
				$("#left-panel").addClass("on");
			} else {
				$("#left-panel").removeClass("on");
			}
		}
	})

</script>

<style>
	@media (min-width: 1200px) {
		.left_panel.on {
			width: 22.5%;
			position: fixed;
			bottom: 72px;
			padding-right: 30px;
		}
	}

		@media (min-width: 992px) and (max-width:1199px) {
		.left_panel.on {
			width: 242.5px;
			position: fixed;
			bottom: 72px;
			padding-right: 30px;
		}
	}

	@media (min-width: 768px) and (max-width:991px){
		.left_panel.on {
			width: 187.5px;
			position: fixed;
			bottom: 72px;
			padding-right: 30px;
		}
	}
</style>