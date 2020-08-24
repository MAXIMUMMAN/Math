package com.maxiumman.myapplication;

import java.util.Stack;

public class Core
{
    public static double Calculate(String in)
    {
        String output = getFormat(in);
        return Counting(output);
    }
   public static String getFormat(String in)
    {
        StringBuilder output = new StringBuilder();
        Stack<Character> operStack = new Stack<>();
        for (int i = 0; i < in.length(); i++) //Для каждого символа в входной строке
        {
            //Разделители пропускаем
            if (IsDelimeter(in.charAt(i)))
                continue; //Переходим к следующему символу

            //Если символ - цифра, то считываем все число
            if (Character.isDigit(in.charAt(i))) //Если цифра
            {
                //Читаем до разделителя или оператора, что бы получить число
                while (!IsDelimeter(in.charAt(i)) && !IsOperator(in.charAt(i)))
                {
                    output.append(in.charAt(i)); //Добавляем каждую цифру числа к нашей строке
                    i++; //Переходим к следующему символу

                    if (i == in.length()) break; //Если символ - последний, то выходим из цикла
                }

                output.append(" "); //Дописываем после числа пробел в строку с выражением
                i--; //Возвращаемся на один символ назад, к символу перед разделителем
            }

            //Если символ - оператор
            if (IsOperator(in.charAt(i))) //Если оператор
            {
                if (in.charAt(i) == '(') //Если символ - открывающая скобка
                    operStack.push(in.charAt(i)); //Записываем её в стек
                else if (in.charAt(i) == ')') //Если символ - закрывающая скобка
                {
                    //Выписываем все операторы до открывающей скобки в строку
                    char s = operStack.pop();

                    while (s != '(')
                    {
                        output.append( s + " ");
                        s = operStack.pop();
                    }
                }
                else //Если любой другой оператор
                {
                    if (operStack.size() > 0) //Если в стеке есть элементы
                        if (GetPriority(in.charAt(i)) <= GetPriority(operStack.peek())) //И если приоритет нашего оператора меньше или равен приоритету оператора на вершине стека
                            output.append(operStack.pop().toString()).append(" "); //То добавляем последний оператор из стека в строку с выражением

                    operStack.push(in.charAt(i)); //Если стек пуст, или же приоритет оператора выше - добавляем операторов на вершину стека

                }
            }
        }

        //Когда прошли по всем символам, выкидываем из стека все оставшиеся там операторы в строку
        while (operStack.size() > 0)
            output.append(operStack.pop()).append(" ");

        return output.toString(); //Возвращаем выражение в постфиксной записи
    }
    private static double Counting(String in)
    {
        double result = 0; //Результат
        Stack<Double> temp = new Stack<>(); //стек для решения

        for (int i = 0; i < in.length(); i++) //Для каждого символа в строке
        {
            //Если символ - цифра, то читаем все число и записываем на вершину стека
            if (Character.isDigit(in.charAt(i)))
            {
                String a = "";

                while (!IsDelimeter(in.charAt(i)) && !IsOperator(in.charAt(i))) //Пока не разделитель
                {
                    a += in.charAt(i); //Добавляем
                    i++;
                    if (i == in.length()) break;
                }
                temp.push(Double.parseDouble(a)); //Записываем в стек
                i--;
            }
            else if (IsOperator(in.charAt(i))) //Если символ - оператор
            {
                //Берем два последних значения из стека
                double a = temp.pop();
                double b = temp.pop();

                switch (in.charAt(i)) //И производим над ними действие, согласно оператору
                {
                    case '+': result = b + a; break;
                    case '-': result = b - a; break;
                    case '*': result = b * a; break;
                    case '/': result = b / a; break;
                    case '^': result = Double.parseDouble(String.valueOf(Math.pow(Double.parseDouble(String.valueOf(b)), Double.parseDouble(String.valueOf(a))))); break;
                }
                temp.push(result); //Результат вычисления записываем обратно в стек
            }
        }
        return temp.peek(); //Забираем результат всех вычислений из стека и возвращаем его
    }
    static private boolean IsDelimeter(char c)
    {
        return (" =".indexOf(c) != -1);
    }
    static private boolean IsOperator(char с)
    {
        return ("+-/*^()".indexOf(с) != -1);
    }
    static private byte GetPriority(char s)
    {
        switch (s)
        {
            case '(': return 0;
            case ')': return 1;
            case '+': return 2;
            case '-': return 3;
            case '*': return 4;
            case '/': return 4;
            case '^': return 5;
            default: return 6;
        }
    }
}
