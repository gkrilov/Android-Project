google.load("visualization", "1", {
	packages: ["corechart"]
});
google.setOnLoadCallback(drawChart);
function drawChart() {
	var data = new google.visualization.DataTable(),
      measurements = ["Calories", "Sodium"],
      rows = [];

	data.addColumn("string", "Measurement");
	data.addColumn("number", "Recommended Intake");
	data.addColumn("number", "My Intake");

  rows[0] = [measurements[0], 1000, 400];
  rows[1] = [measurements[1], 500, 350];

	data.addRows(rows);

	var options = {
		title: "Recommended Intake vs. \nMy Intake",
		vAxis: {
			title: "Measurements",
			titleTextStyle: {
				color: "red"
			}//,
      // textStyle: {
      //   fontSize: 20
      // }
		},
    fontSize: 30,
    legend: "top"//,
    // legendTextStyle: {
    //   fontSize: 20
    // }
	};

	var chart = new google.visualization.BarChart(document.getElementById("chart_div"));
	chart.draw(data, options);
}

