const COLORS = [
    '#FF6633', '#FFB399', '#FF33FF', '#FFFF99', '#00B3E6',
    '#E6B333', '#3366E6', '#999966', '#99FF99', '#B34D4D',
    '#80B300', '#809900', '#E6B3B3', '#6680B3', '#66991A',
    '#FF99E6', '#CCFF1A', '#FF1A66', '#E6331A', '#33FFCC',
    '#66994D', '#B366CC', '#4D8000', '#B33300', '#CC80CC',
    '#66664D', '#991AFF', '#E666FF', '#4DB3FF', '#1AB399',
    '#E666B3', '#33991A', '#CC9999', '#B3B31A', '#00E680',
    '#4D8066', '#809980', '#E6FF80', '#1AFF33', '#999933',
    '#FF3380', '#CCCC00', '#66E64D', '#4D80CC', '#9900B3',
    '#E64D66', '#4DB380', '#FF4D4D', '#99E6E6', '#6666FF'
];
const blogTrendCtx = document.getElementById('myChart').getContext('2d');
const blogTrendChart = new Chart(blogTrendCtx, {
    type: 'bar',
    data: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
                label: '# of Views',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        },
        plugins: {
            title: {
                display: true,
                text: 'Blog Category Trend Chart',
                position: 'bottom',
                font: {
                    size: 16
                }
            }
        }
    }
});

//Show amount registration chart by date
const registrationCtx = document.getElementById('registrationChart').getContext('2d');
const registrationChart = new Chart(registrationCtx, {
    type: 'line',
    data: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
                label: '# of Registration',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: '#6f42c1',
                borderColor: '#6610f2',
                borderWidth: 1,
                tension: 0.1
            }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        },
        plugins: {
            title: {
                display: true,
                text: 'Registration Chart',
                position: 'bottom',
                font: {
                    size: 16
                }
            }
        }
    }
});

//Show subjects and  amount course in each subject
const subjectCtx = document.getElementById("subjectChart").getContext('2d');
const subjectChart = new Chart(subjectCtx, {
    type: 'doughnut',
    data: {
        labels: [
            'Red',
            'Blue',
            'Yellow',
        ],
        datasets: [{
                label: '',
                data: [300, 50, 100],
                backgroundColor: COLORS,
                borderColor: '#f8f9fa',
                hoverOffset: 4
            }]
    },
    options: {
        plugins: {
            title: {
                display: true,
                text: 'Subject & Course Chart',
                position: 'bottom',
                font: {
                    size: 16
                }
            },
            legend: {
                position: 'bottom'
            }
        }
    }
});

// Show posiblity an account will enrolled courses belong to a subject
const subjectEnrollTrendCtx = document.getElementById("subjectEnrollTrend").getContext('2d');
const subjectEnrollTrend = new Chart(subjectEnrollTrendCtx, {
    type: 'radar',
    data: {
        labels: [],
        datasets: [{
                label: 'The probability that a user enrolls in a course on this subject',
                data: [],
                fill: true,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgb(255, 99, 132)',
                pointBackgroundColor: 'rgb(255, 99, 132)',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'rgb(255, 99, 132)'
            }]
    },
    options: {
        plugins: {
            legend: {
                display: false
            },
            title: {
                display: true,
                text: 'Possibly Interested In A Subject Of An Account',
                position: 'bottom',
                font: {
                    size: 16
                }
            }
        }
    }
});

// Show revenue in each month
const revenueCtx = document.getElementById('revenueChart').getContext('2d');
const revenueChart = new Chart(revenueCtx, {
    type: 'line',
    data: {
        labels: [],
        datasets: [{
                data: [],
                label: "Income",
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)'
                ],
                borderWidth: 3

            }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    callback: function (value, index, values) {
                        if (parseInt(value) >= 1000) {
                            return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                        } else {
                            return '$' + value;
                        }
                    }
                }
            }
        },
        plugins: {
            legend: {
                display: false
            }
        }
    }
});

// Show revenue of each subject
const subjectRevenueCtx = document.getElementById('subjectRevenueChart').getContext('2d');
const subjectRevenueChart = new Chart(subjectRevenueCtx, {
    type: 'bar',
    data: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
                label: '',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: '#0d6efd',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true,
                ticks: {
                    callback: function (value, index, values) {
                        if (parseInt(value) >= 1000) {
                            return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                        } else {
                            return '$' + value;
                        }
                    }
                }
            }
        },
        plugins: {
            title: {
                display: true,
                text: 'Subject Revenue Chart',
                position: 'bottom',
                font: {
                    size: 14
                }
            }
        }
    }
});

function drawCourseSubjectChart() {
    fetch("../api/statistics/coursesubject")
            .then(resp => resp.json())
            .then(data => {
                let subjects = data.map(e => e.subject)
                let amountCourseInEachSubject = data.map(e => e.amount);
                subjectChart.data.labels = subjects;
                subjectChart.data.datasets[0].data = amountCourseInEachSubject;
                subjectChart.update();
            })
            .catch(error => console.log(error))
}

function drawCourseEnrollTable() {
    let tableContent = document.getElementById("courseEnrollTable").querySelector("tbody");
    fetch("../api/statistics/enrollcourse")
            .then(resp => resp.json())
            .then(data => {
                let n = 1;
                for (let course of data) {
                    let row = document.createElement("tr");
                    let courseIdCell = document.createElement("td");
                    courseIdCell.innerHTML = (n <= 3) ? `<span class="fw-bold">${n++}</span>`
                            : `<span>${n++}</span>`
                    let courseNameCell = document.createElement("td");
                    courseNameCell.textContent = course.courseName;
                    let numberOfEnrollCell = document.createElement("td");
                    numberOfEnrollCell.textContent = course.numberOfEnroll;

                    row.appendChild(courseIdCell);
                    row.appendChild(courseNameCell);
                    row.appendChild(numberOfEnrollCell);

                    tableContent.appendChild(row);
                }
            })
            .catch(error => console.log(error))
}


function drawRevenueChart(startDate, endDate) {
    fetch(`../api/statistics/revenue/${startDate}/${endDate}`)
            .then(resp => resp.json())
            .then(data => {
                let total = data.map(e => e.revenue).reduce((a, b) => a + b, 0);
                $("#revenueInTime").text(total.toFixed(2));
                revenueChart.data.labels = data.map(e => `${e.date.year}/${e.date.month}/${e.date.day}`);
                revenueChart.data.datasets[0].data = data.map(e => e.revenue);
                revenueChart.update();
            })
            .catch(error => console.log(error));
}

function drawBlogTrendChart() {
    fetch("../api/statistics/blogcategorytrend")
            .then(resp => resp.json())
            .then(data => {
                let blogCategories = data.map(e => e.blogCategoryName);
                let amountViews = data.map(e => e.numberOfView);
                blogTrendChart.data.labels = blogCategories;
                blogTrendChart.data.datasets[0].data = amountViews;
                blogTrendChart.update();
            })
            .catch(error => console.log(error));
}

function drawSubjectEnrollTrend() {
    fetch("../api/statistics/amount_account_subject")
            .then(resp => resp.json())
            .then(data => {
                let subjectNames = data.map(e => e.subjectName);
                let amountEnrolled = data.map(e => e.amountEnrolled / e.totalAccount);
                subjectEnrollTrend.data.labels = subjectNames;
                subjectEnrollTrend.data.datasets[0].data = amountEnrolled;
                subjectEnrollTrend.update();
            })
            .catch(error => console.log(error));
}

function drawRegistrationChart(from, to) {
    fetch(`../api/statistics/registration/${from}/${to}`)
            .then(resp => resp.json())
            .then(data => {
                let total = data.map(e => e.amount).reduce((a, b) => a + b, 0);
                registrationChart.data.datasets[0].label = `Total Registration ${total}`;
                registrationChart.data.labels = data.map(e => `${e.date.month}/${e.date.day}/${e.date.year}`);
                registrationChart.data.datasets[0].data = data.map(e => e.amount);
                registrationChart.update();
            })
            .catch(error => console.log(error));
}

const fromMonthInput = document.getElementById("fromMonth");
const toMonthInput = document.getElementById("toMonth");

function searchRevenueEventHandler() {
    let str1 = fromMonthInput.value;
    let str2 = toMonthInput.value;
    const pattern = /^(\d?\d)(\/\d{4})$/;

    const styleInvalidInput = (input, borderStyle) => input.style.borderRight = borderStyle;
    let invalidBorderStyle = "5px red solid";

    styleInvalidInput(fromMonthInput, "");
    styleInvalidInput(toMonthInput, "");


    if (!pattern.test(str1)) {
        styleInvalidInput(fromMonthInput, invalidBorderStyle);
        return;
    }

    if (!pattern.test(str2)) {
        styleInvalidInput(toMonthInput, invalidBorderStyle);
        return;
    }

    let tokens = str1.split("/");
    let m1 = parseInt(tokens[0]);
    let y1 = parseInt(tokens[1]);
    tokens = str2.split("/");
    let m2 = parseInt(tokens[0]);
    let y2 = parseInt(tokens[1]);

    if (m1 > 12) {
        styleInvalidInput(fromMonthInput, invalidBorderStyle);
        return;
    }

    if (m2 > 12) {
        styleInvalidInput(toMonthInput, invalidBorderStyle);
        return;
    }

    drawRevenueChart(m1, y1, m2, y2);
}

function drawSubjectRevenueChart(start, end) {
    fetch(`../api/statistics/revenue-subject/${start}/${end}`)
            .then(resp => resp.json())
            .then(data => {
                let total = data.map(e => e.revenue).reduce((a, b) => a + b, 0);
                subjectRevenueChart.data.datasets[0].label = `Total $${total}`;
                subjectRevenueChart.data.labels = data.map(e => e.subject.name);
                subjectRevenueChart.data.datasets[0].data = data.map(e => e.revenue);
                subjectRevenueChart.update();
            })
            .catch(error => console.log(error));
}

// Search Revenue Event
$("#revenueStartDate").on("change", searchRevenueEventHandler)
$("#revenueEndDate").on("change", searchRevenueEventHandler)

function searchRevenueEventHandler() {
    drawRevenueChart($("#revenueStartDate").val(), $("#revenueEndDate").val());
}

// Search Subject's Revenue Event
$("#subjectRevenueStartDate").on("change", searchSubjectRevenueEventHandler);
$("#subjectRevenueEndDate").on("change", searchSubjectRevenueEventHandler);

function searchSubjectRevenueEventHandler() {
    drawSubjectRevenueChart($("#subjectRevenueStartDate").val(), $("#subjectRevenueEndDate").val());
}

// Search Regestritation Amount
$("#registrationStartDate").on("change", searchRegistrationEventHandler);
$("#registrationEndDate").on("change", searchRegistrationEventHandler);

function searchRegistrationEventHandler() {
    drawRegistrationChart($("#registrationStartDate").val(), $("#registrationEndDate").val());
}

drawRevenueChart($("#revenueStartDate").val(), $("#revenueEndDate").val());
drawSubjectRevenueChart($("#subjectRevenueStartDate").val(), $("#subjectRevenueEndDate").val())
drawRegistrationChart($("#registrationStartDate").val(), $("#registrationEndDate").val())
drawSubjectEnrollTrend();
drawCourseSubjectChart();
drawBlogTrendChart();

