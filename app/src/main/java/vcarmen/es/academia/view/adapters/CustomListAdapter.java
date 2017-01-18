package vcarmen.es.academia.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import vcarmen.es.academia.R;
import vcarmen.es.academia.model.Alumno;
import vcarmen.es.academia.model.Asignatura;
import vcarmen.es.academia.model.Matricula;

public class CustomListAdapter extends BaseAdapter {
    private List list;
    private int layout;
    private Context context;

    public CustomListAdapter (List list, int layout, Context context) {
        this.list = list;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (list != null && list.size() > 0) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            if (list.get(0) instanceof Alumno) {
                TextView dni, nombre, apellidos, eMail;

                dni = (TextView) view.findViewById(R.id.resultadoDni);
                dni.setText(((List<Alumno>) list).get(i).getDni());

                nombre = (TextView) view.findViewById(R.id.resultadoNombre);
                nombre.setText(((List<Alumno>) list).get(i).getNombre());

                apellidos = (TextView) view.findViewById(R.id.resultadoApellidos);
                apellidos.setText(((List<Alumno>) list).get(i).getApellidos());

                eMail = (TextView) view.findViewById(R.id.resultadoEmail);
                eMail.setText(((List<Alumno>) list).get(i).getEmail());
            }
            else if (list.get(0) instanceof Asignatura) {
                TextView nombre, ciclo, curso, horas;

                nombre = (TextView) view.findViewById(R.id.resultadoNombreAsignatura);
                nombre.append(((List<Asignatura>) list).get(i).getNombre());

                ciclo = (TextView) view.findViewById(R.id.resultadoCicloAsignatura);
                ciclo.append(((List<Asignatura>) list).get(i).getCiclo());

                curso = (TextView) view.findViewById(R.id.resultadoCursoAsignatura);
                curso.append(((List<Asignatura>) list).get(i).getCurso());

                horas = (TextView) view.findViewById(R.id.resultadoHorasAsignatura);
                horas.append(((List<Asignatura>) list).get(i).getHoras()+"");
            }
            else if (list.get(0) instanceof Matricula) {
                TextView nombre, apellidos, asignatura, fechaInicio, fechaFin;

                nombre = (TextView) view.findViewById(R.id.textViewNombreMatricula);
                nombre.append(((List<Matricula>) list).get(i).getNombre());

                apellidos = (TextView) view.findViewById(R.id.textViewApellidosMatricula);
                apellidos.append(((List<Matricula>) list).get(i).getApellidos());

                asignatura = (TextView) view.findViewById(R.id.textViewAsinaturaMatricula);
                asignatura.append(((List<Matricula>) list).get(i).getAsigantura());

                fechaInicio = (TextView) view.findViewById(R.id.textViewFechaInicioMatricula);
                String year, month, day;
                //year = ((List<Matricula>) list).get(i).getFechaInicio().getYear() + "";
                //month = ((List<Matricula>) list).get(i).getFechaInicio().getMonth() + 1 + "";
                //day = ((List<Matricula>) list).get(i).getFechaInicio().getDay() + "";
                //fechaInicio.append(year+"-"+month+"-"+day);
                fechaInicio.append(((List<Matricula>) list).get(i).getFechaInicio().toLocaleString());

                fechaFin = (TextView) view.findViewById(R.id.textViewFechaFinMatricula);
                //year = ((List<Matricula>) list).get(i).getFechaFin().getYear() + "";
                //month = ((List<Matricula>) list).get(i).getFechaFin().getMonth() + 1 + "";
                //day = ((List<Matricula>) list).get(i).getFechaFin().getDay() + "";
                //fechaFin.append(year+"-"+month+"-"+day);
                fechaFin.append(((List<Matricula>) list).get(i).getFechaFin().toLocaleString());
            }

            return view;
        }
        else
            return null;

    }
}
