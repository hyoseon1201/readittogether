import { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';

const DoughnutChart = ({ dataLabels, dataValues }) => {
  const chartRef = useRef(null);
  const chartInstanceRef = useRef(null);

  useEffect(() => {
    if (chartInstanceRef.current) {
      chartInstanceRef.current.destroy(); // 이전에 생성된 차트 제거
    }

    if (dataLabels.length === 0 || dataValues.length === 0) return; // 데이터가 없을 경우 렌더링하지 않음

    // dataValues 배열에서 값이 0인 데이터 필터링하여 새로운 배열 생성
    const filteredDataValues = dataValues.filter(value => value !== 0);
    // dataValues의 값이 0이 아닌 데이터에 해당하는 dataLabels 필터링하여 새로운 배열 생성
    const filteredDataLabels = dataLabels.filter((_, index) => dataValues[index] !== 0);

    const canvas = chartRef.current;
    const ctx = canvas.getContext('2d');

    // 차트 생성
    chartInstanceRef.current = new Chart(ctx, {
      type: 'doughnut',
      data: {
        // labels: dataLabels,
        labels: filteredDataLabels,
        datasets: [{
          // data: dataValues,
          data: filteredDataValues,
          backgroundColor: getRandomColors(filteredDataValues.length)
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false
      }
    });

    return () => {
      if (chartInstanceRef.current) {
        chartInstanceRef.current.destroy(); // 컴포넌트가 언마운트될 때 차트 제거
      }
    };
  }, [dataLabels, dataValues]);

  // 임의의 색상 생성
  const getRandomColors = (count) => {
    const colors = [];
    for (let i = 0; i < count; i++) {
      const color = `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.6)`;
      colors.push(color);
    }
    return colors;
  };

  return <canvas ref={chartRef} />;
};

export default DoughnutChart;
