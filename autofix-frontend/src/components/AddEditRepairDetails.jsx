import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import repairService from "../services/repair.service";
import repairPricesListService from "../services/repairPricesList.service";
import vehicleService from "../services/vehicle.service";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import InputLabel from "@mui/material/InputLabel";
import SaveIcon from "@mui/icons-material/Save";
import { LocalizationProvider, DatePicker, TimePicker } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";

const AddEditRepairDetails = () => {
  const [repairId, setRepairId] = useState("");
  const [repairType, setRepairType] = useState("");
  const [repairDate, setRepairDate] = useState(null);
  const [repairTime, setRepairTime] = useState(null);
  const [repairAmount, setRepairAmount] = useState(0);
  const [repairTypes, setRepairTypes] = useState([]);
  const [engineType, setEngineType] = useState("");
  const { id, vehicleId } = useParams();
  const navigate = useNavigate();
  const isEdit = Boolean(id);

  useEffect(() => {
    if (isEdit) {
      repairService.getRepairDetailsById(id)
        .then(response => {
          const detail = response.data;
          setRepairId(detail.repairId || "");
          setRepairType(detail.repairType || "");
          setRepairDate(detail.repairDate ? dayjs(detail.repairDate) : null);
          setRepairTime(detail.repairTime ? dayjs(detail.repairTime, "HH:mm:ss") : null);
        })
        .catch(error => {
          console.log("Error fetching repair detail.", error);
        });
    }

    // Obtener el tipo de motor del vehículo
    vehicleService.get(vehicleId)
      .then(response => {
        setEngineType(response.data.engineType || "");
      })
      .catch(error => {
        console.log("Error fetching vehicle details.", error);
      });
  }, [id, isEdit, vehicleId]);

  useEffect(() => {
    repairPricesListService.getRepairTypes()
      .then(response => {
        setRepairTypes(response.data);
      })
      .catch(error => {
        console.log("Error fetching repair types.", error);
      });
  }, []);

  const saveRepairDetail = (e) => {
    e.preventDefault();
    const formattedRepairDate = repairDate ? repairDate.format("YYYY-MM-DD") : null;
    const formattedRepairTime = repairTime ? repairTime.format("HH:mm:ss") : null;

    console.log("repairType:", repairType);
    console.log("engineType:", engineType);

    repairPricesListService.getRepairPrice(repairType, engineType)
      .then(response => {
        const price = response.data;
        const repairDetail = {
          repairId,
          repairType,
          repairDate: formattedRepairDate,
          repairTime: formattedRepairTime,
          repairAmount: price
        };

        const action = isEdit ?
          repairService.updateRepairDetails(id, repairDetail) :
          repairService.createRepairDetails([repairDetail]);
        action
          .then(response => {
            console.log(`${isEdit ? "Updated" : "Added"} repair detail successfully.`, response.data);
            navigate(`/repairs/details/vehicle/${vehicleId}`);
          })
          .catch(error => {
            console.error(`Error ${isEdit ? "updating" : "adding"} repair detail.`, error);
          });
      })
      .catch(error => {
        console.log("Error fetching repair price.", error);
      });
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Box
        display="flex"
        flexDirection="column"
        alignItems="center"
        justifyContent="center"
        component="form"
        onSubmit={saveRepairDetail}
        sx={{ mt: 4, width: '100%', maxWidth: 600 }}
      >
        <h3>{isEdit ? "Editar Detalle de Reparación" : "Agregar Detalle de Reparación"}</h3>
        <hr />
        <FormControl fullWidth sx={{ mb: 2 }}>
          <TextField
            label="ID de Reparación"
            type="number"
            value={repairId}
            onChange={e => setRepairId(e.target.value)}
            sx={{ mb: 1 }}
          />
        </FormControl>
        <FormControl fullWidth sx={{ mb: 2 }}>
          <InputLabel id="repair-type-label">Tipo de Reparación</InputLabel>
          <Select
            labelId="repair-type-label"
            value={repairType}
            onChange={e => setRepairType(e.target.value)}
            label="Tipo de Reparación"
            sx={{ mb: 1 }}
          >
            {repairTypes.map((type) => (
              <MenuItem key={type} value={type}>
                {type}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <DatePicker
          label="Fecha de Reparación"
          value={repairDate}
          onChange={setRepairDate}
          slotProps={{ textField: { fullWidth: true, sx: { mb: 2 } } }}
        />
        <TimePicker
          label="Hora de Reparación"
          value={repairTime}
          onChange={setRepairTime}
          slotProps={{ textField: { fullWidth: true, sx: { mb: 2 } } }}
        />
        <FormControl fullWidth sx={{ mb: 2 }}>
          <TextField
            label="Monto de Reparación"
            type="number"
            value={repairAmount}
            sx={{ mb: 2 }}
            InputProps={{ readOnly: true }}
          />
        </FormControl>
        <Button variant="contained" color="primary" type="submit" startIcon={<SaveIcon />} sx={{ mt: 2 }}>
          Guardar
        </Button>
        <hr />
      </Box>
    </LocalizationProvider>
  );
};

export default AddEditRepairDetails;