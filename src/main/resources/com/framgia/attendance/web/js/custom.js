!function($) {

	$(function() {
		console.log($("#registerbtn").length > 0);
		if ($("#registerbtn").length > 0) {

			$('body')
					.on(
							'click',
							'#registerbtn',
							function() {
								var startTime = $("#startTime").val();
								var endTime = $("#endTime").val();

								var startTimeHour = startTime / 100;
								var startTimeMin = startTime
										- ((startTime / 100) * 100);
								var endTimeHour = endTime / 100;
								var endTimeMin = endTime
										- ((endTime / 100) * 100);
								var workTimeHour = endTimeHour - startTimeHour;
								var workTimeMin = endTimeMin - startTimeMin;
								if (workTimeMin < 0) {
									// 勤務時間(分)が0未満になる場合、勤務時間(時間)を1時間マイナスしたうえで、勤務時間(分)を60分プラスする
									workTimeHour--;
									workTimeMin += MIN_PER_ONE_HOUR;
								}

								var timeLag = workTimeHour * 60 + workTimeMin;

								var msg = "登録内容を更新します。宜しいですか？";
								if (timeLag >= 360 && timeLag < 480) {
									msg = "Warning：\n勤務時間が6時間以上、8時間未満の場合、定められた休憩時間は45分です。このまま登録して宜しいですか？";
								} else if (timeLag >= 480) {
									msg = "Warning：\n勤務時間が8時間以上の場合、定められた休憩時間は60分です。このまま登録して宜しいですか？";
								}
								if (!confirm(msg)) {
									return false;
								}
							});

		}

		if ($(".reject").length <= 0) {
			console.log($(".reject").length);
			$(".reject_title").css("display", "none");

		}

		if ($("#hintdate").length > 0) {
			console.log($("#hintdate").text().replace("*", ""));
			$('.date_picker').datepicker({
				format : $("#hintdate").text().replace("*", "")

			}).on(
					'changeDate',
					function(ev) {
						if ($("#payVacLeftJoinId").val() == "") {
							$("#payVacationLeftJoinDateId").val("");
						} else {
							$("#payVacationLeftJoinDateId").val(
									$("#employmentDateId").val());
						}
					});

			$("#employmentDateId").on(
					"change",
					function(ev) {
						if ($("#payVacLeftJoinId").val() == "") {
							$("#payVacationLeftJoinDateId").val("");
						} else {
							$("#payVacationLeftJoinDateId").val(
									$("#employmentDateId").val());
						}
					});

			$("#payVacLeftJoinId").on(
					"blur",
					function(ev) {
						if ($("#payVacLeftJoinId").val() == "") {
							$("#payVacationLeftJoinDateId").val("");
						} else {
							$("#payVacationLeftJoinDateId").val(
									$("#employmentDateId").val());
						}
					});

			$("#payVacLeftJoinTempId").on("blur", function(ev) {
				if ($("#payVacLeftJoinTempId").val() == "") {
					$("#payVacationLeftJoinTempDateId").val("");
				}
			});

			$("#payVacLeftLastYearId").on("blur", function(ev) {
				if ($("#payVacLeftLastYearId").val() == "") {
					$("#payVacationLeftLastYearDateId").val("");
				}
			});

			$("#payVacLeftThisYearId").on("blur", function(ev) {
				if ($("#payVacLeftThisYearId").val() == "") {
					$("#payVacationLeftThisYearDateId").val("");
				}
			});

		} else {
			$('.date_picker').datepicker({
				format : "yyyy/mm/dd"
			});
		}
		
		var listth = $('#mainTable').find('th');
		$('#headerTable').find('th').each(function(index) {
			console.log($(this).width());
			listth[index].width($(this).width());

		});

	});



}(window.jQuery);