public int minArray(int[] numbers) {
    int min = numbers[0];
    for (int i = 1; i < numbers.length; i++) {
        if (min > numbers[i])
            min = numbers[i];
    }
    return min;
}

