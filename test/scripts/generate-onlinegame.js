function generateRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function generateTestCase() {
    let clans = [];
    const groupCount = generateRandomInt(1, 1000);

    for (let i = 0; i < 20000; i++) {
        const numberOfPlayers = generateRandomInt(1, groupCount);
        const points = generateRandomInt(1, 100000);

        clans.push({
            numberOfPlayers,
            points
        });
    }

    const testCase = {
        groupCount,
        clans
    };

    return JSON.stringify(testCase, null, 4);
}

console.log(generateTestCase());