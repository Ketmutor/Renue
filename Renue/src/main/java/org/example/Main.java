

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

class MapString implements Comparable<MapString>
{
    String str="";
    int index;
    public int compareTo (MapString mp)
    {

        return str.compareTo(mp.str);

    }

    public boolean contains(String strr)
    {
        return str.contains(strr);


    }

    public int indexOF(String strr)
    {
        return str.indexOf(strr);
    }

}

class MapInteger implements Comparable<MapInteger>
{
    String str="";
    int index;
    public int compareTo (MapInteger mp)
    {
        if(index>mp.index)
            return 1;
        else
            return 0;

    }

    public boolean contains(String strr)
    {
        if( (str.indexOf(strr)==0))

            return true;
        else
            return false;

    }

    public int indexOF(String strr)
    {
        return str.indexOf(strr);
    }
    public MapInteger(int ind,String strr)
    {
        str=strr;
        index=ind;
    }
}

class Filter
{
    public String [] arr=null;
}
class Main {

    static MapString[] arr= new MapString[7185];
	static String filename =null;
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        int i=1;//переменная для цикла
        int j=0;//переменная для цикла

        for(int k=0;k<7185;k++)
        {
            arr[k]=new MapString();
        }
		Scanner in = new Scanner(System.in);
		while(true)
		{
		System.out.print("Введите полный путь до файла: ");
		 filename = in.nextLine();
		
		if((new File(filename)).exists())
		{
			//if(!((new File(filename)).isDirectory()))
		break;
		
		}
		}
		
			
        try(Scanner scanner = new Scanner(new File(filename), "UTF-8")){
            while(scanner.hasNextLine())
            {

                String line = scanner.nextLine();


                j=line.indexOf(",\"")+2;
                while(line.charAt(j+1)!=',')
                {

                    arr[i].str+=line.charAt(j);
                    j++;


                }

                arr[i].index=i;

                i++;
            }
        }
        String query="";
        //String filter="";
        

        Filter filter=null;
        while(true)
        {
            filter= Fillter(in);
            System.out.print("Введите название аэропорта: ");
            query= in.nextLine();
            Read(query,filter);
            if(query.indexOf("!quit")==0)
            {
                break;
            }
        }
        in.close();


    }

    public static void Read(String Name_OF_Airport, Filter filter) throws FileNotFoundException, InterruptedException
    {
        TreeSet <MapString> str = new TreeSet<MapString>() ;//словарь названий
        TreeSet <Integer> numbers = new TreeSet<Integer>() ;//отобраные под определённый запрос по названиям
        String [] array = new String[7185];

        try(Scanner scanner = new Scanner(new File(filename), "UTF-8")){
            {

                int count=0;
                long startTime = System.nanoTime();
                for(int k=1;k<7185;k++)
                {
                    //проверка по славарю названий, подходит ил нет аэропорт

                    if(arr[k].indexOF(Name_OF_Airport)==0)
                    {
                        str.add(arr[k]);
                        numbers.add(arr[k].index);

                    }

                }

                String line = null;
                int i=1;
                for(Integer in:numbers)
                {
                    while(i!=in)
                    {
                        //получение полной строки из отобранных в предыдущем цикле
                        line = scanner.nextLine();
                        i++;
                    }
                    line = scanner.nextLine();


                    array[i]=line;
                    i++;
                }
                int flag;
                for(MapString mp:str)
                {
                    //проверка по фильтрам и/или просто вывод, используется переменная str, так как в ней все название уже отсортированы
                    String filterable=array[mp.index];
                    String [] Array= new String[14];
                    String str_value=filterable;
                    filterable =filterable.replace("\"","");
                    String Str="";
                    int h=0;
                    int[] helper=null;
                    if(filter.arr!=null)
                    helper = new int[(filter.arr.length+1)/2];//массив, хранящий значения сравнений с каждым фильтром

                    if(filter.arr!=null&&filter.arr.length>2)
                    {
                        //форматирование строки под сравнение с фильтром
                        for (int k = 0; k < 14; k++) {
                            for (; ; h++) {
                                if (filterable.charAt(h) == ',' || h == filterable.length() - 1) {
                                    break;
                                } else
                                    Str += filterable.charAt(h);
                            }
                            h++;

                            Array[k] = Str;
                            Str = "";

                        }
                        //заполнение массива helper		
                        for (int j = 0; j < filter.arr.length; j = j + 4) {
                            switch (filter.arr[j + 1]) {
                                case ">":
                                    //System.out.println(Array[Integer.parseInt(filter.arr[j])-1]+"    "+filter.arr[j + 2]+" "+j+" "+filter.arr.length);
                                    if(Integer.parseInt(filter.arr[j])==1)
                                    {

                                        Integer int1=Integer.parseInt((Array[Integer.parseInt(filter.arr[j])-1]));
                                        Integer int2 = Integer.parseInt(filter.arr[j + 2]);
                                        if(int1>int2)
                                            helper[j/4]=1;
                                            else
                                            helper[j/4]=0;
                                    }
                                    else
                                    if((Array[Integer.parseInt(filter.arr[j])-1].compareTo(filter.arr[j + 2]))>0)
                                    helper[j/4]=1;
                                    else
                                        helper[j/4]=0;
                                    break;
                                case "<":
                                    if(Integer.parseInt(filter.arr[j])==1)
                                    {

                                        Integer int1=Integer.parseInt((Array[Integer.parseInt(filter.arr[j])-1]));
                                        Integer int2 = Integer.parseInt(filter.arr[j + 2]);
                                        if(int1<int2)
                                            helper[j/4]=1;
                                        else
                                            helper[j/4]=0;
                                    }
                                    else
                                    if(!((Array[Integer.parseInt(filter.arr[j])-1].compareTo(filter.arr[j + 2]))>0))
                                        helper[j/4]=1;
                                    else

                                        helper[j/4]=0;
                                    break;
                                case "<>":
                                    if(Integer.parseInt(filter.arr[j])==1)
                                    {

                                        Integer int1=Integer.parseInt((Array[Integer.parseInt(filter.arr[j])-1]));
                                        Integer int2 = Integer.parseInt(filter.arr[j + 2]);
                                        if(int1!=int2)
                                            helper[j/4]=1;
                                        else
                                            helper[j/4]=0;
                                    }
                                    else
                                    if (!((filter.arr[j + 2].equals(Array[Integer.parseInt(filter.arr[j])-1]))))
                                        helper[j/4]=1;
                                    else

                                        helper[j/4]=0;
                                    break;
                                case "=":
                                    if(Integer.parseInt(filter.arr[j])==1)
                                    {

                                        Integer int1=Integer.parseInt((Array[Integer.parseInt(filter.arr[j])-1]));
                                        Integer int2 = Integer.parseInt(filter.arr[j + 2]);
                                        if(int1==int2)
                                            helper[j/4]=1;
                                        else
                                            helper[j/4]=0;
                                    }
                                    else
                                    if (((filter.arr[j + 2].equals(Array[Integer.parseInt(filter.arr[j])-1])))) {
                                        helper[j/4]=1;

                                        //System.out.println(Array[Integer.parseInt(filter.arr[j])-1]+"    "+filter.arr[j + 2]+" "+j+" "+filter.arr.length+" ");
                                    }
                                    else {
                                        helper[j/4]=0;
                                    }
                                        //System.out.println(flag);
                                    break;

                            }


                        }
                        int bu=helper[0];
                        //обработчик операций & и ||
                        if(helper.length>1)
                            for (int j = 3; j<filter.arr.length-1; j+=4 )
                        {

                            if(filter.arr[j].equals("&"))
                            {
                                //System.out.println(filter.arr[j]);
                                //System.out.println(helper[j/4+1]+"     ");
                                bu=bu*helper[j/4+1];
                            }else
                            {
                                if(bu==1)
                                {
                                    bu=1;
                                    break;
                                }else
                                    bu=helper[j/4+1];
                            }
                        }
                        if(bu==1) {
                            //вывод строки, если она прошла филтр
                            count++;
                            System.out.println("" + str_value);
                        }
                    }
                    else
                    {
                        //вывод строки, если она подошла по названию и нет фильтров
                        count++;
                        System.out.println(str_value);
                    }

                    //System.out.println(flag);
                }
                long endTime = System.nanoTime();
                long Time =   endTime-startTime;

                System.out.println("Количество найденных строк: "+count+" Время, затраченное на поиск: "+Time/1000000);
            }
        }
    }


    public static  Filter Fillter(Scanner in)
    {
        //возвращает фильтр
        String filter ;
        Filter filt=new Filter();
        Boolean bool = true;
        String[] array=null;
        while(bool)
        {
            System.out.print("Введите фильтер аэропорта, не учитывая 7,8,9,10 столбцы(если не нужен, то просто нажмите enter): ");
            int count=0;
            filter = in.nextLine();
            if(filter=="")
                break;
            while(filter.contains(" "))//преобразование строки в набор данных фильтра
                filter =filter.replace(" ","");
            filter =filter.replace("column[","");
            filter =filter.replace("]","");
            filter =filter.replace(">"," > ");
            filter =filter.replace("<"," < ");
            filter =filter.replace("&"," & ");
            filter =filter.replace("||"," || ");
            filter =filter.replace(" <  > "," <> ");
            filter =filter.replace("="," = ");
            array = filter.split(" ");
            for(int b=0;b<array.length;b++)
            {
                System.out.println(array[b]);
            }
            //проверка фильтра на корректность
            if((array.length>40)||array.length%4<3)
            {
                System.out.println("Неправильно введённый фильтр!\nПопробуйте снова!");
                continue;
            }
            for(int i=1;i<array.length;i=i+4)
            {
                try
                {

                    if(array[i].equals(">")||array[i].equals("<")||array[i].equals("<>")||array[i].equals("="))
                    {
                    }
                    else
                    {
                        throw new IllegalArgumentException();
                    }
                }
                catch (IllegalArgumentException  e)
                {
                    System.out.println("Неправильно введённый фильтр!\nПопробуйте снова!1");
                    count++;
                }
            }
            for(int i=3;i<array.length;i=i+4)
            {
                try
                {
                    if(!((array[i].equals("&"))||(array[i].equals("||")))) {
                        throw new IllegalArgumentException();
                    }
                }
                catch (IllegalArgumentException  e)
                {
                    System.out.println("Неправильно введённый фильтр!\nПопробуйте снова3!");
                    count++;
                }
            }
            for(int i=0;i<array.length;i=i+4)
            {
                try
                {
                    Integer Int = Integer.parseInt(array[i]);
                    //System.out.println(Int);
                    if(Int==7||Int==8||Int==9||Int==10)
                    {
                            throw new IllegalArgumentException();
                    }
                    if(Int.equals(null) ){
                        throw new IllegalArgumentException();
                    }
                }
                catch (IllegalArgumentException  e)
                {
                    System.out.println("Неправильно введённый фильтр!\nПопробуйте снова!0");
                    count++;
                }
            }
            if(count==0) {
                break;
            }
        }
        if(array!=null)
        if("&"==array[array.length-1]||"||"==array[array.length-1])
        {
            array[array.length]="";
        }
        filt.arr=array;
        return filt;
    }
}
