import React from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, LineElement, CategoryScale, LinearScale, PointElement, Title, Tooltip, Legend } from 'chart.js';

// Registra todos los elementos necesarios
ChartJS.register(LineElement, PointElement, CategoryScale, LinearScale, Title, Tooltip, Legend);

// Función para formatear fechas en yyyy-mm-dd
const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Los meses van de 0 a 11
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
};

const PredictionChart = ({ predictions }) => {
    const labels = predictions.prediccion.map(p => `${formatDate(p.periodoInicio)} - ${formatDate(p.periodoFin)}`);
    const dataPoints = predictions.prediccion.map(p => p.valor);

    const data = {
        labels: labels,
        datasets: [
            {
                label: 'Predicción de Ventas',
                data: dataPoints,
                borderColor: 'rgba(75, 192, 192, 1)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderWidth: 1,
                fill: true,
            },
        ],
    };

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            tooltip: {
                callbacks: {
                    label: (context) => `Predicción: ${context.raw}`,
                },
            },
        },
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Periodo',
                },
                ticks: {
                    autoSkip: true,
                    maxTicksLimit: 10,
                },
            },
            y: {
                title: {
                    display: true,
                    text: 'Valor',
                },
                beginAtZero: true,
            },
        },
    };

    return (

        <div className='rounded' style={{ position: 'relative', width: '90%', height: '90%', backgroundColor: '#F1EEED'}}>
            <Line data={data} options={options} />
        </div>
    );
};

export default PredictionChart;
